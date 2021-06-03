package com.liu.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liu.community.mapper.UserMapper;
import com.liu.community.model.User;
import com.liu.community.model.UserExample;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;

	
	public void createUpdate(User user) {
		UserExample userExample=new UserExample();
		userExample.createCriteria()
					.andAccountIdEqualTo(user.getAccountId());
		List<User> users=userMapper.selectByExample(userExample);
		if(users.size()==0) {
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			userMapper.insert(user);
		}else {
			User dbuser=users.get(0);
			User updateUser=new User();
			updateUser.setGmtModified(System.currentTimeMillis());
			updateUser.setAvatarUrl(user.getAvatarUrl());
			updateUser.setName(user.getName());
			updateUser.setToken(user.getToken());
			UserExample example=new UserExample();
			example.createCriteria().andIdEqualTo(dbuser.getId());
			userMapper.updateByExampleSelective(updateUser, example);
			
		}
	}
}
