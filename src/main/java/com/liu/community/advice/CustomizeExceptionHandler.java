package com.liu.community.advice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.alibaba.fastjson.JSON;
import com.liu.community.dto.ResultDTO;
import com.liu.community.exception.CustomizeErrorCode;
import com.liu.community.exception.CustomizeException;

@ControllerAdvice
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(Exception.class)
	ModelAndView handle(HttpServletRequest request,Throwable ex,Model model,HttpServletRequest httpServletRequest,HttpServletResponse response) {
		String contentType=httpServletRequest.getContentType();
		if(contentType.equals("application/json")) {
			ResultDTO resultDTO=null;
			if(ex instanceof CustomizeException) {
				resultDTO =ResultDTO.errorOf((CustomizeException)ex);
			}else {
				resultDTO =ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
			}
			response.setContentType("application/json");
			response.setStatus(200);
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.write(JSON.toJSONString(resultDTO));
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}else {
			if(ex instanceof CustomizeException) {
				model.addAttribute("message",ex.getMessage());
			}else {
				model.addAttribute("message","未知错误");
			}
			return new ModelAndView("error");
		}
		
	}
}
