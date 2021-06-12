package com.liu.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.liu.community.mapper")
@EnableScheduling
public class JavaCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaCommunityApplication.class, args);
	}

}
