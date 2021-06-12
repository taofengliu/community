package com.liu.community.provider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

@Component
public class CloudProvider {
	
	private static String secretId="AKIDd4E7j7F5ez3qMdgSxCBtJTO3RYOyHJAo";

	private static String secretKey="PXP0KI3artfBRRBfuvhDUVGl12nIXXHl";
	
	static COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
	static Region region = new Region("ap-beijing");
	static ClientConfig clientConfig = new ClientConfig(region);
	static {clientConfig.setHttpProtocol(HttpProtocol.https);}
	static COSClient cosClient = new COSClient(cred, clientConfig);
	
		
	public String upload(InputStream inputStream,String mimeType,String fileName) {
		String[] filePath = fileName.split("\\.");
		String generateFileName="";
		if(filePath.length>1) {
			generateFileName=UUID.randomUUID().toString()+"."+filePath[filePath.length-1];
		}else {
			return null;
		}
		
		String bucketName = "singerpigman-1306220163";
		ObjectMetadata objectMetadata = new ObjectMetadata();
		// 设置输入流长度为500
		try {
			objectMetadata.setContentLength(inputStream.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 设置 Content type, 默认是 application/octet-stream
		objectMetadata.setContentType(mimeType);
		PutObjectResult putObjectResult = cosClient.putObject(bucketName, generateFileName, inputStream, objectMetadata);
		String etag = putObjectResult.getETag();
		// 关闭输入流...
		Date expiration = new Date(new Date().getTime() + 24*60*60*60*365*10);
		URL url = cosClient.generatePresignedUrl(bucketName, generateFileName, expiration);

		return url.toString();
	}
}
