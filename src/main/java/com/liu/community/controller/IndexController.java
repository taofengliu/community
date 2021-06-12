package com.liu.community.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.liu.community.cache.HotTagCache;
import com.liu.community.dto.HotTagDTO;
import com.liu.community.dto.Pagination;
import com.liu.community.service.QuestionService;

@Controller
public class IndexController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private HotTagCache hotTagCache;
	@GetMapping("/")
	public String index(HttpServletRequest request,Model model,@RequestParam(name="page",defaultValue = "1") Integer page,
			@RequestParam(name = "size" ,defaultValue = "10") Integer size,@RequestParam(name="search",required = false) String search,@RequestParam(name="tag",required = false) String tag) {
		 @SuppressWarnings("rawtypes")
		Pagination pagination=questionService.list(search,tag,page,size);
		 List<String> tags=hotTagCache.getHots();
		 model.addAttribute("pagination",pagination);
		 model.addAttribute("search",search);
		 model.addAttribute("tags",tags);
		 model.addAttribute("tag",tag);
		 return "index";
	}
}
