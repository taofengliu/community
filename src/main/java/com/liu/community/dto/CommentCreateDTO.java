package com.liu.community.dto;

public class CommentCreateDTO {
	private Long parentId;
	private String content;
	private Integer type;
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String comment) {
		this.content = comment;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
