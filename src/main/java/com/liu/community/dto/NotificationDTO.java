package com.liu.community.dto;

import com.liu.community.model.User;

public class NotificationDTO {
	private Long id;

	private Long gmtCreate;
	private Long outerid;
	
	private Integer status;
	private Long notifier;
	private String notifierName;
	private String outerTitle;
	private String typeName;
	private Integer type;
	
	
	
	public Integer getType() {
		return type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getOuterid() {
		return outerid;
	}
	public void setOuterid(Long outerid) {
		this.outerid = outerid;
	}
	public String getNotifierName() {
		return notifierName;
	}
	public void setNotifierName(String notifierName) {
		this.notifierName = notifierName;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getNotifier() {
		return notifier;
	}
	public void setNotifier(Long notifier) {
		this.notifier = notifier;
	}
	public String getOuterTitle() {
		return outerTitle;
	}
	public void setOuterTitle(String outerTitle) {
		this.outerTitle = outerTitle;
	}
	
}
