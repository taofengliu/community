package com.liu.community.dto;


public class HotTagDTO implements Comparable<HotTagDTO>{
	private String name;
	private Integer priority;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	@Override
	public int compareTo(HotTagDTO o) {
		return this.getPriority()-o.getPriority();
	}
	
}
