package com.liu.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.liu.community.dto.CommentDTO;
import com.liu.community.dto.QuestionDTO;
import com.liu.community.enums.CommentType;
import com.liu.community.service.CommentService;
import com.liu.community.service.QuestionService;


@Controller
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping("/question/{id}")
	public String  question(@PathVariable(name="id") Long id,Model model) {
		QuestionDTO questionDTO=questionService.getById(id);
		List<QuestionDTO> ralatedQuestions=questionService.selectRelated(questionDTO);
		List<CommentDTO> comments=commentService.listByTargetId(id,CommentType.QUESTION);
		questionService.incview(id);
		model.addAttribute("question", questionDTO);
		model.addAttribute("comments",comments);
		model.addAttribute("relatedQuestions",ralatedQuestions);
		return "question";
	}
}
