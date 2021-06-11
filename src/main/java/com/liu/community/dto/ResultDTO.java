package com.liu.community.dto;

import com.liu.community.exception.CustomizeErrorCode;
import com.liu.community.exception.CustomizeException;

public class ResultDTO<T> {
	private Integer code;
	private String message;
	private T data;
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public static ResultDTO errorOf(Integer code,String message) {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setCode(code);
		resultDTO.setMessage(message);
		return resultDTO;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public static ResultDTO errorOf(CustomizeErrorCode noLogin) {
		return errorOf(noLogin.getCode(),noLogin.getMessage());
	}
	public static ResultDTO okOf() {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setCode(200);
		resultDTO.setMessage("评论成功");
		return resultDTO;
	}
	public static ResultDTO errorOf(CustomizeException ex) {
		return errorOf(ex.getCode(),ex.getMessage());
	}
	public static <T> ResultDTO okOf(T t) {
		ResultDTO<Object> resultDTO = new ResultDTO<>();
		resultDTO.setCode(200);
		resultDTO.setMessage("请求成功");
		resultDTO.setData(t);
		return resultDTO;
		
	}
}
