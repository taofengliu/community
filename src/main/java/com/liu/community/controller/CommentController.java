package com.liu.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommentController {
	
	@RequestMapping(value="/comment",method=RequestMethod.POST)
	public Object post() {
		
	}
}
