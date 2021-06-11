package com.liu.community.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liu.community.dto.CommentCreateDTO;
import com.liu.community.dto.CommentDTO;
import com.liu.community.dto.ResultDTO;
import com.liu.community.enums.CommentType;
import com.liu.community.exception.CustomizeErrorCode;
import com.liu.community.model.Comment;
import com.liu.community.model.User;
import com.liu.community.service.CommentService;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@ResponseBody
	@RequestMapping(value="/comment",method=RequestMethod.POST)
	public Object post(@RequestBody CommentCreateDTO commentDTO,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if(user==null) {
			return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
		}
		if(commentDTO==null||StringUtils.isBlank(commentDTO.getContent())) {
			return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
		}
		Comment comment = new Comment();
		comment.setParentId(commentDTO.getParentId());
		comment.setContent(commentDTO.getContent());
		comment.setType(commentDTO.getType());
		comment.setGmtModified(System.currentTimeMillis());
		comment.setGmtCreate(System.currentTimeMillis());
		comment.setCommentator(user.getId());
		comment.setLikeCount(0L);
		comment.setCommentCount(0);
		commentService.insert(comment,user);
		return ResultDTO.okOf();
	}

	@ResponseBody
	@RequestMapping(value="/comment/{id}",method=RequestMethod.GET)
	public ResultDTO<List<CommentDTO>> comments(@PathVariable(name="id") Long id) {
		List<CommentDTO> listByQuestionId = commentService.listByTargetId(id,CommentType.COMMENT);
		return ResultDTO.okOf(listByQuestionId);
	}
}
