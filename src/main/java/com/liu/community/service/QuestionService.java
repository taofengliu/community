package com.liu.community.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.util.SpringContentTypeUtils;

import com.liu.community.dto.Pagination;
import com.liu.community.dto.QuestionDTO;
import com.liu.community.dto.QuestionQueryDTO;
import com.liu.community.exception.CustomizeErrorCode;
import com.liu.community.exception.CustomizeException;
import com.liu.community.mapper.QuestionExtMapper;
import com.liu.community.mapper.QuestionMapper;
import com.liu.community.mapper.UserMapper;
import com.liu.community.model.Question;
import com.liu.community.model.QuestionExample;
import com.liu.community.model.User;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionMapper questionMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private QuestionExtMapper questionExtMapper;
	
	public Pagination list(String search,String tag,Integer page, Integer size) {
		if(StringUtils.isNotBlank(search)) {
			String[] split = StringUtils.split(search,' ');
			search = Arrays.stream(split).collect(Collectors.joining("|"));
		}
		
		QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
		questionQueryDTO.setSearch(search);
		questionQueryDTO.setTag(tag);
		Integer totalCount = (int) questionExtMapper.countBySearch(questionQueryDTO);
		Integer totalPage;
		if(totalCount%size==0) {
			totalPage=totalCount/size;
		}else {
			totalPage=totalCount/size+1;
		}
		if(page<1) page=1;
		else if(page>totalPage) page=totalPage;
		if(page==0) page=1;
		Integer offset=size*(page-1);
		QuestionExample questionExample = new QuestionExample();
		questionExample.setOrderByClause("gmt_create desc");
		questionQueryDTO.setSize(size);
		questionQueryDTO.setPage(offset);
		List<Question> questions=questionExtMapper.selectBySearch(questionQueryDTO);
		List<QuestionDTO> questionDTOList=new ArrayList<>();
		Pagination pagination = new Pagination();
		for(Question question:questions) {
			User user=userMapper.selectByPrimaryKey(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}
		pagination.setData(questionDTOList);
		
		
		
		pagination.setPagination(totalPage,page);
		return pagination;
	}

	public Pagination list(Long userId, Integer page, Integer size) {
		QuestionExample questionExample = new QuestionExample();
		questionExample.createCriteria().andCreatorEqualTo(userId);
		Integer totalCount = (int) questionMapper.countByExample(questionExample);
		Integer totalPage;
		if(totalCount%size==0) {
			totalPage=totalCount/size;
		}else {
			totalPage=totalCount/size+1;
		}
		if(page<1) page=1;
		else if(page>totalPage) page=totalPage;
		if(page==0) page=1;
		Integer offset=size*(page-1);
		QuestionExample questionExample2 = new QuestionExample();
		questionExample2.createCriteria().andCreatorEqualTo(userId);
		List<Question> questions=questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample2,new RowBounds(offset,size));
		List<QuestionDTO> questionDTOList=new ArrayList<>();
		Pagination pagination = new Pagination();
		for(Question question:questions) {
			User user=userMapper.selectByPrimaryKey(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}
		pagination.setData(questionDTOList);
		
		
		
		pagination.setPagination(totalPage,page);
		return pagination;
	}

	public QuestionDTO getById(Long id) {
		// TODO Auto-generated method stub
		Question question= questionMapper.selectByPrimaryKey(id);
		if(question==null) {
			throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
		}
		QuestionDTO quesiotnDTO=new QuestionDTO();
		BeanUtils.copyProperties(question, quesiotnDTO);
		User user=userMapper.selectByPrimaryKey(question.getCreator());
		quesiotnDTO.setUser(user);
		return quesiotnDTO;
	}

	public void createUpdate(Question question) {
		if(question.getId()==null) {
			question.setGmtCreate(System.currentTimeMillis());
			question.setGmtModified(question.getGmtCreate());
			question.setCommentCount(0);
			question.setVeiwCount(0);
			question.setLikeCount(0);
			questionMapper.insert(question);
		}else {
			
			Question question2 = new Question();
			question2.setGmtModified(question.getGmtCreate());
			question2.setTitle(question.getTitle());
			question2.setDescription(question.getDescription());
			question2.setTag(question.getTag());
			QuestionExample questionExample = new QuestionExample();
			questionExample.createCriteria().andIdEqualTo(question.getId());
			int updateByExampleSelective = questionMapper.updateByExampleSelective(question2,questionExample);
			if(updateByExampleSelective!=1) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
		}
		
	}

	public void incview(Long id) {
		QuestionExample ques=new QuestionExample();
		ques.createCriteria().andIdEqualTo(id);
		List<Question> selectByExample = questionMapper.selectByExample(ques);
		Question record=new Question();
		record.setId(id);
		record.setVeiwCount(selectByExample.get(0).getVeiwCount());
		questionExtMapper.incVeiw(record);
	}

	public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
		if(StringUtils.isBlank(questionDTO.getTag())) {
			return new ArrayList<QuestionDTO>();
		}
		String[] split = StringUtils.split(questionDTO.getTag(),',');
		String collect = Arrays.stream(split).collect(Collectors.joining("|"));
		Question question = new Question();
		question.setId(questionDTO.getId());
		question.setTag(collect);
		List<Question> selectRelated = questionExtMapper.selectRelated(question);
		List<QuestionDTO> collect2 = selectRelated.stream().map(q->{
			QuestionDTO questionDTO2 = new QuestionDTO();
			BeanUtils.copyProperties(q, questionDTO2);
			return questionDTO2;
			}).collect(Collectors.toList());
		return collect2;
	}



}
