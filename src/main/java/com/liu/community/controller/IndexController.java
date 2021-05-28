package com.liu.community.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.liu.community.dto.QuestionDTO;
import com.liu.community.mapper.QuestionMapper;
import com.liu.community.mapper.UserMapper;
import com.liu.community.model.Question;
import com.liu.community.model.User;
import com.liu.community.service.QuestionService;

import java.util.List;

@Controller
public class IndexController {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private QuestionService questionService;
	@GetMapping("/")
	public String index(HttpServletRequest request,Model model) {
		Cookie[] cookies=request.getCookies();
		if(cookies!=null)
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("token")){
					String token=cookie.getValue();
					User user=userMapper.findByToken(token);
					if(user!=null) {
						request.getSession().setAttribute("user", user);
					}
					break;
				}
			}
		 List<QuestionDTO> questionList=questionService.list();
		 model.addAttribute("questions",questionList);
		 return "index";
	}
}
