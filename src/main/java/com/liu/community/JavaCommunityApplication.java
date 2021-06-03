package com.liu.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.liu.community.mapper")
public class JavaCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaCommunityApplication.class, args);
	}

}
