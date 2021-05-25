package com.liu.community.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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
							HttpServletRequest request ) {
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(redirectUri);
		accessTokenDTO.setClient_id(clientId);
		accessTokenDTO.setClient_secret(clientSecret);
		accessTokenDTO.setState(state);
		String token=githubProvider.getAccessToken(accessTokenDTO);
		GithubUser githubuser = githubProvider.getUser(token);
		if(githubuser!=null) {
			User user=new User();
			user.setToken(UUID.randomUUID().toString());
			user.setName(githubuser.getName());
			user.setAccountId(String.valueOf(githubuser.getId()));
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			userMapper.insert(user);
			request.getSession().setAttribute("user", githubuser);
			return "redirect:/";
		}else {
			return "redirect:/";
		}
	}
}
