package com.liu.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublishControler {
	@GetMapping("/publish")
	public String publish() {
		return "publish";
	}
}
