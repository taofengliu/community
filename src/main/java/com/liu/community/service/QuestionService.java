package com.liu.community.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liu.community.dto.Pagination;
import com.liu.community.dto.QuestionDTO;
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
	
	public Pagination list(Integer page, Integer size) {
		Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
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
		List<Question> questions=questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
		List<QuestionDTO> questionDTOList=new ArrayList<>();
		Pagination pagination = new Pagination();
		for(Question question:questions) {
			User user=userMapper.selectByPrimaryKey(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}
		pagination.setQuestions(questionDTOList);
		
		
		
		pagination.setPagination(totalPage,page);
		return pagination;
	}

	public Pagination list(Integer userId, Integer page, Integer size) {
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
		pagination.setQuestions(questionDTOList);
		
		
		
		pagination.setPagination(totalPage,page);
		return pagination;
	}

	public QuestionDTO getById(Integer id) {
		// TODO Auto-generated method stub
		Question question= questionMapper.selectByPrimaryKey(id);
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
			questionMapper.insert(question);
		}else {
			
			Question question2 = new Question();
			question2.setGmtModified(question.getGmtCreate());
			question2.setTitle(question.getTitle());
			question2.setDescription(question.getDescription());
			question2.setTag(question.getTag());
			QuestionExample questionExample = new QuestionExample();
			questionExample.createCriteria().andIdEqualTo(question.getId());
			questionMapper.updateByExampleSelective(question2,questionExample);
		}
		
	}



}
