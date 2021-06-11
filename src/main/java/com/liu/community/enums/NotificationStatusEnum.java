package com.liu.community.enums;

public enum NotificationStatusEnum {
	UNREAD(0),READ(1);
	
	private int status;
	NotificationStatusEnum(int i) {
		this.status=i;
	}
	public int getStatus() {
		return this.status;
	}
	
}
