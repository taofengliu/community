package com.liu.community.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.stereotype.Component;

import com.liu.community.dto.HotTagDTO;

@Component
public class HotTagCache {
	private Map<String,Integer> tags=new HashMap<>();
	private List<String> hots=new ArrayList<>();
	public Map<String, Integer> getTags() {
		return tags;
	}

	public void setTags(Map<String, Integer> tags) {
		this.tags = tags;
	}
	
	public List<String> getHots() {
		return hots;
	}

	public void setHots(List<String> hots) {
		this.hots = hots;
	}

	public void updateTags(Map<String,Integer> tags) {
		int max=10;
		PriorityQueue<HotTagDTO> priorityQueue=new PriorityQueue<>(max);
		tags.forEach((name,priority)->{
			HotTagDTO hotTagDTO=new HotTagDTO();
			hotTagDTO.setName(name);
			hotTagDTO.setPriority(priority);
			if(priorityQueue.size()<=max) {
				priorityQueue.add(hotTagDTO);
			}else {
				HotTagDTO peek = priorityQueue.peek();
				if(peek.compareTo(hotTagDTO)<0) {
					priorityQueue.poll();
					priorityQueue.add(hotTagDTO);
				}
			}
		});
		ArrayList<String> sortedTags = new ArrayList<>();
		HotTagDTO poll = priorityQueue.poll();
		hots.add(poll.getName());
		while(poll!=null) {
			sortedTags.add(0,poll.getName());
			poll=priorityQueue.poll();
		}
		hots=sortedTags;
	}
}
