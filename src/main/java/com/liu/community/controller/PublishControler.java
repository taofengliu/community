package com.liu.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.liu.community.cache.TagCache;
import com.liu.community.dto.QuestionDTO;
import com.liu.community.model.Question;
import com.liu.community.model.User;
import com.liu.community.service.QuestionService;

@Controller
public class PublishControler {

	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/publish/{id}")
	public String edit(@PathVariable(name="id") Long id,Model model) {
		QuestionDTO question = questionService.getById(id);
		model.addAttribute("title",question.getTitle());
		model.addAttribute("description", question.getDescription());
		model.addAttribute("tag",question.getTag());
		model.addAttribute("id",id);
		model.addAttribute("tags",TagCache.get());
		return "/publish";
	}
	
	@GetMapping("/publish")
	public String publish(Model model) {
		model.addAttribute("tags",TagCache.get());
		return "publish";
	}
	
	@PostMapping("/publish")
	public String doPublish(
			@RequestParam(value = "title",required = false) String title,
			@RequestParam(value = "description",required = false) String description,
			@RequestParam(value = "tag",required = false) String tag,
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "id",required = false) Long id
			) {
		model.addAttribute("title",title);
		model.addAttribute("description", description);
		model.addAttribute("tag",tag);
		model.addAttribute("tags",TagCache.get());
		if(title==null||title=="") {
			model.addAttribute("error","标题不能为空");
			return "publish";
		}
		if(description==null||description=="") {
			model.addAttribute("error","问题补充不能为空");
			return "publish";
		}
		if(tag==null||tag=="") {
			model.addAttribute("error","标签不能为空");
			return "publish";
		}
		String filterInvalid = TagCache.filterInvalid(tag);
		if(StringUtils.isNotBlank(filterInvalid)) {
			model.addAttribute("error","输入非法标签:"+filterInvalid);
			return "publish";
		}
		
		User user=(User) request.getSession().getAttribute("user");
		if(user==null) {
			model.addAttribute("error","用户未登录");
			return "publish";
		}
		Question question=new Question();
		question.setTitle(title);
		question.setDescription(description);
		question.setTag(tag);
		question.setCreator(user.getId());
		question.setId(id);
		questionService.createUpdate(question);
		return "redirect:/";
		
	}
}
