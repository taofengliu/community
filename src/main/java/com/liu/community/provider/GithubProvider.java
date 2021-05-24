package com.liu.community.provider;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.liu.community.dto.AccessTokenDTO;
import com.liu.community.dto.GithubUser;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class GithubProvider {
	public String getAccessToken(AccessTokenDTO accessTokenDTO) {
		MediaType m= MediaType.get("application/json; charset=utf-8");
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(m, JSON.toJSONString(accessTokenDTO));
		Request request = new Request.Builder()
										      .url("https://github.com/login/oauth/access_token")
										      .post(body)
										      .build();
	    try (Response response = client.newCall(request).execute()) {
	    	String string=response.body().string();
	    	String[] sp=string.split("&");
	    	String t=sp[0];
	    	String[] split = t.split("=");
	    	return split[1];
	    } catch (Exception e) {
			e.printStackTrace();
		}
	    return null;
	}
	
	public GithubUser getUser(String accessToken) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
			      .url("https://api.github.com/user")
			      .header("Authorization","token "+accessToken)
			      .build();
		try {
			Response response = client.newCall(request).execute();
			String string=response.body().string();
			GithubUser githubUser= JSON.parseObject(string,GithubUser.class);
			return githubUser;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
