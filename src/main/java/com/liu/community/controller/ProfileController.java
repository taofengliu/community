package com.liu.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.liu.community.dto.Pagination;
import com.liu.community.model.User;
import com.liu.community.service.NotificationService;
import com.liu.community.service.QuestionService;

@Controller
public class ProfileController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("profile/{action}")
	public String profile(@PathVariable(name="action") String action,Model model, HttpServletRequest request,@RequestParam(name="page",defaultValue = "1") Integer page,
			@RequestParam(name = "size" ,defaultValue = "10") Integer size) {
		User user=(User) request.getSession().getAttribute("user");
		if(user==null) return "redirect:/";
		if(action.equals("questions")) {
			model.addAttribute("section","questions");
			model.addAttribute("sectionName","我的发布");
			Pagination list = questionService.list(user.getId(),page,size);
			model.addAttribute("pagination",list);
		}else if("replies".equals(action)) {
			Pagination pagination=notificationService.list(user.getId(),page,size);
			model.addAttribute("section","replies");
			model.addAttribute("sectionName","最新回复");
			model.addAttribute("pagination",pagination);
			model.addAttribute("unreadCount",notificationService.unreadCount(user.getId()));
		}
		
		return "profile";
	}
}
