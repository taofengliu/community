package com.liu.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.liu.community.dto.NotificationDTO;
import com.liu.community.enums.NotificationEnum;
import com.liu.community.model.User;
import com.liu.community.service.NotificationService;

@Controller
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@GetMapping("notification/{id}")
	public String profile(@PathVariable(name="id") Long id,HttpServletRequest request) {
		User user=(User) request.getSession().getAttribute("user");
		if(user==null) return "redirect:/";
		NotificationDTO notificationDTO= notificationService.read(id,user);
		if(NotificationEnum.REPLY_COMMENT.getType()==notificationDTO.getType()
				||NotificationEnum.REPLY_QUESTION.getType()==notificationDTO.getType()) {
			return "redirect:/question/"+notificationDTO.getOuterid();
		}else {
			return "profile";
		}
		
	}
}
