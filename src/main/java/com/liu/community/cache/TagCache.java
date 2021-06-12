package com.liu.community.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.liu.community.dto.TagDTO;

public class TagCache {
	public static List<TagDTO> get(){
		List<TagDTO> arrayList = new ArrayList<TagDTO>();
		TagDTO program = new TagDTO();
		program.setCategoryName("开发语言");
		program.setTags(Arrays.asList("js","php","css","html","Java","Python","node.js","C++","golang"));
		TagDTO framework = new TagDTO();
		framework.setCategoryName("平台框架");
		framework.setTags(Arrays.asList("Spring Boot","Spring","struts"));
		TagDTO server = new TagDTO();
		server.setCategoryName("服务器");
		server.setTags(Arrays.asList("Linux","Nginx","centos","docker"));
		TagDTO database = new TagDTO();
		database.setCategoryName("数据库和缓存");
		database.setTags(Arrays.asList("MySQL","Redis","Oracle"));
		arrayList.add(program);
		arrayList.add(framework);
		arrayList.add(database);
		arrayList.add(server);
		return arrayList;
	}
	
	public static String filterInvalid(String tags) {
		String[] split = StringUtils.split(tags,",");
		
		List<TagDTO> tagDTOs=get();
		List<String> tagList= tagDTOs.stream().flatMap(tag->tag.getTags().stream()).collect(Collectors.toList());
	    String invalid=Arrays.stream(split).filter(t->{
	    	for(String s:tagList) {
	    		t=t.trim();
	    		s=s.trim();
	    		if(s.equals(t)) {
	    			System.out.println(false);
	    			return false;
	    		}
	    	}
			return true;
	    }).collect(Collectors.joining(","));
		return invalid;
	}
}