package com.liu.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.liu.community.model.User;

@Mapper
public interface UserMapper {
	@Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
	void insert(User user);
}
