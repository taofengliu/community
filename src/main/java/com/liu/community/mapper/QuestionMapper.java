package com.liu.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.liu.community.model.Question;

@Mapper
public interface QuestionMapper {
	
	@Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
	public void create(Question question);

	@Select("select * from question")
	public List<Question> list();
}
