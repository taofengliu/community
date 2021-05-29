package com.liu.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liu.community.dto.Pagination;
import com.liu.community.dto.QuestionDTO;
import com.liu.community.mapper.QuestionMapper;
import com.liu.community.mapper.UserMapper;
import com.liu.community.model.Question;
import com.liu.community.model.User;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionMapper questionMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	public Pagination list(Integer page, Integer size) {
		Integer totalCount = questionMapper.count();
		Integer totalPage;
		if(totalCount%size==0) {
			totalPage=totalCount/size;
		}else {
			totalPage=totalCount/size+1;
		}
		if(page<1) page=1;
		if(page>totalPage) page=totalPage;
		Integer offset=size*(page-1);
		
		List<Question> questions=questionMapper.list(offset,size);
		List<QuestionDTO> questionDTOList=new ArrayList<>();
		Pagination pagination = new Pagination();
		for(Question question:questions) {
			User user=userMapper.findById(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}
		pagination.setQuestions(questionDTOList);
		
		
		
		pagination.setPagination(totalCount,page,size);
		return pagination;
	}

}
