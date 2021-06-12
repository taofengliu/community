package com.liu.community.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.liu.community.cache.HotTagCache;
import com.liu.community.mapper.QuestionMapper;
import com.liu.community.model.Question;
import com.liu.community.model.QuestionExample;

import lombok.extern.slf4j.Slf4j;

@Component
public class HotTagTasks {
	
	@Autowired
	private QuestionMapper questionMapper;
	
	@Autowired
	private HotTagCache hotTagCache;
	
	@Scheduled(fixedRate=60*60*24)
	public void hotTagSchedule() {
		int offset=0;
		int limit=20;
		Map<String, Integer> priorities = new HashMap<>();
		List<Question> list=new ArrayList<>();
		while(offset==0||list.size()==limit) {
			
			list=questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset,limit));
			for(Question question:list) {
				
				
				String[] tags = StringUtils.split(question.getTag(),",");
				for(String tag:tags) {
					Integer priority=priorities.get(tag);
					if(priority!=null) {
						priorities.put(tag, priority+5+question.getCommentCount());
					}else {
						priorities.put(tag, 5+question.getCommentCount());
					}
				}
			}
			offset+=limit;
		}
		hotTagCache.updateTags(priorities);
	}
}
