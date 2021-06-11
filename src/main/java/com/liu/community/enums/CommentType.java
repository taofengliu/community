package com.liu.community.enums;

public enum CommentType {
	QUESTION(1),COMMENT(2);
	private Integer type;
	
	public Integer getType() {
		return type;
	}

	private CommentType(Integer type) {
		this.type=type;
	}

	public static boolean isExist(Integer type2) {
		for(CommentType c:CommentType.values()) {
			if(c.getType()==type2) {
				return true;
			}
		}
		return false;
	}
}
