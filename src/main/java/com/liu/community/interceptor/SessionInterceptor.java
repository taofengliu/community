package com.liu.community.interceptor;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.liu.community.mapper.UserMapper;
import com.liu.community.model.UserExample;

@Service
public class SessionInterceptor implements HandlerInterceptor{
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		Cookie[] cookies=request.getCookies();
		if(cookies!=null) 
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("token")){
					String token=cookie.getValue();
					UserExample userExample = new UserExample();
					userExample.createCriteria()
								.andTokenEqualTo(token);
					List<com.liu.community.model.User> users = userMapper.selectByExample(userExample);
					if(users.size()!=0) {
						request.getSession().setAttribute("user", users.get(0));
					}
					break;
				}
			}
		return true;
	}

	
}
