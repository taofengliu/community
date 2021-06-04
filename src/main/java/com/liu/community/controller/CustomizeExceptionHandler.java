package com.liu.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.liu.community.exception.CustomizeException;

@ControllerAdvice
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(Exception.class)
	ModelAndView handle(HttpServletRequest request,Throwable ex,Model model) {
		if(ex instanceof CustomizeException) {
			model.addAttribute("message",ex.getMessage());
		}else {
			model.addAttribute("message","未知错误");
		}
		
		return new ModelAndView("error");
	}
}
