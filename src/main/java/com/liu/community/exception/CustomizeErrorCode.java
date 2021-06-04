package com.liu.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
	QUESTION_NOT_FOUND("问题不存在");
	public String getMessage() {
		return message;
	}
	private String message;
	CustomizeErrorCode(String message){
		this.message=message;
	}
}
