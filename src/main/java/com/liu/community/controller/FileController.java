package com.liu.community.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.liu.community.dto.FileDTO;
import com.liu.community.provider.CloudProvider;

@Controller
public class FileController {
	@Autowired
	private CloudProvider cloudProvider;
	
	@RequestMapping("/file/upload")
	@ResponseBody
	public FileDTO upload(HttpServletRequest request) {
		MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest) request;
		MultipartFile file = mRequest.getFile("editormd-image-file");
		try {
			String url=cloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
			FileDTO fileDTO = new FileDTO();
			fileDTO.setSuccess(1);
			fileDTO.setUrl(url);
			return fileDTO;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
