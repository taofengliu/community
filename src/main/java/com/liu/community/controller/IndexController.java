package com.liu.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.liu.community.dto.Pagination;
import com.liu.community.service.QuestionService;

@Controller
public class IndexController {

	@Autowired
	private QuestionService questionService;
	@GetMapping("/")
	public String index(HttpServletRequest request,Model model,@RequestParam(name="page",defaultValue = "1") Integer page,
			@RequestParam(name = "size" ,defaultValue = "10") Integer size) {
		 Pagination pagination=questionService.list(page,size);
		 model.addAttribute("pagination",pagination);
		 return "index";
	}
}
