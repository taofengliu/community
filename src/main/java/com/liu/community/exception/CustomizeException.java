package com.liu.community.exception;

@SuppressWarnings("serial")
public class CustomizeException extends RuntimeException{
	private String message;
	public CustomizeException(ICustomizeErrorCode code) {
		this.message=code.getMessage();
	}
	public CustomizeException(String message) {
		this.message=message;
	}
	public String getMessage() {
		return message;
	}
}
