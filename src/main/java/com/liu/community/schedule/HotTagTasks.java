package com.liu.community.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HotTagTasks {
	@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {
		
	}
}
