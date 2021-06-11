package com.liu.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
	QUESTION_NOT_FOUND(2001,"问题不存在"),
	TARGET_PARAM__NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
	NO_LOGIN(2003,"用户未登录"),
	SYSTEM_ERROR(2004,"未知错误"),
	TYPE_PARAM_WRONG(2005,"评论类型错误"), 
	COMMENT_NOT_FOUND(20006,"回复的评论不存在"),
	COMMENT_IS_EMPTY(20007,"输入内容不能为空"),
	READ_NOTIFICATION_FAIL(20008,"你干啥呢？"),
	NOTIFICATION_NOT_FOUND(20009,"数据丢失");;
	public String getMessage() {
		return message;
	}
	public Integer getCode() {
		return code;
	}
	private String message;
	private Integer code;
	CustomizeErrorCode(Integer code,String message){
		this.message=message;
		this.code=code;
	}
}
