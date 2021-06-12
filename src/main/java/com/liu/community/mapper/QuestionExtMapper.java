package com.liu.community.mapper;

import java.util.List;

import com.liu.community.dto.QuestionQueryDTO;
import com.liu.community.model.Question;

public interface QuestionExtMapper {
    int incVeiw(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question question);
	int countBySearch(QuestionQueryDTO questionQueryDTO);
	List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}