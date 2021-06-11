package com.liu.community.enums;

public enum NotificationEnum {
	REPLY_QUESTION(1,"回复了问题"),
	REPLY_COMMENT(2,"回复了评论");
	
	private int type;
	private String name;
	NotificationEnum(int i, String string) {
		this.name=string;
		this.type=i;
	}
	public static String nameOf(int type) {
		for(NotificationEnum e:NotificationEnum.values()) {
			if(e.getType()==type) return e.getName();
		}
		return "";
	}
	
	public int getType() {
		return type;
	}
	public void setType(int status) {
		this.type = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
