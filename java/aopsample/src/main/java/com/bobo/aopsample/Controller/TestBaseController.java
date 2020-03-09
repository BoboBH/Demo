package com.bobo.aopsample.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/test")
public class TestBaseController extends BaseRestController{
	
	@Value("${student.name}")
	private String studentName;
	@Value("${test.name}")
	private String name;
	/*@Value("${test.description}")
	private String description;
	*/
	@RequestMapping("/name")
	public String name(){
		return name;
	}
	/*
	@RequestMapping("/description")
	public String description(){
		return description;
	}*/
	

	@RequestMapping("/studentname")
	public String description(){
		return studentName;
	}
	
	@RequestMapping("/authkey")
	public String getAuthorization(){
		String key = this.getJwt();
		if(StringUtils.isEmpty(key))
			return "no authorization";
		return key;
	}

}
