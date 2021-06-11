package com.liu.community.mapper;

import java.util.List;

import com.liu.community.model.Question;

public interface QuestionExtMapper {
    int incVeiw(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question question);
}