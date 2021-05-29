package com.liu.community.controller;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.liu.community.dto.AccessTokenDTO;
import com.liu.community.dto.GithubUser;
import com.liu.community.mapper.UserMapper;
import com.liu.community.model.User;
import com.liu.community.provider.GithubProvider;

@Controller
public class AuthorizeController {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private GithubProvider githubProvider;
	
	@Value("${github.client.id}")
	private String clientId;
	
	@Value("${github.client.secret}")
	private String clientSecret;
	
	@Value("${github.redirect.uri}")
	private String redirectUri;
	
	@GetMapping("/callback")
	public String callback(@RequestParam(name="code") String code,
							@RequestParam(name="state") String state,
							HttpServletRequest request, 
							HttpServletResponse response) {
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(redirectUri);
		accessTokenDTO.setClient_id(clientId);
		accessTokenDTO.setClient_secret(clientSecret);
		accessTokenDTO.setState(state);
		
		String accesstoken=githubProvider.getAccessToken(accessTokenDTO);
		GithubUser githubuser = githubProvider.getUser(accesstoken);
		if(githubuser!=null&&githubuser.getId()!=null) {
			User user=new User();
			String token=UUID.randomUUID().toString();
			user.setToken(token);
			user.setName(githubuser.getName());
			user.setAccountId(String.valueOf(githubuser.getId()));
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			user.setAvatarUrl(githubuser.getAvatar_url());
			userMapper.insert(user);
			response.addCookie(new Cookie("token",token));
			return "redirect:/";
		}else {
			return "redirect:/";
		}
	}
}
