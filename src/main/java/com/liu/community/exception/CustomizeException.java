package com.liu.community.exception;

@SuppressWarnings("serial")
public class CustomizeException extends RuntimeException{
	private String message;
	private Integer code;
	public CustomizeException(ICustomizeErrorCode ecode) {
		this.code=ecode.getCode();
		this.message=ecode.getMessage();
	}
	public CustomizeException(String message) {
		this.message=message;
	}
	public String getMessage() {
		return message;
	}
	public Integer getCode() {
		return code;
	}
}
