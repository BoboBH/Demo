package com.bobo.dubbo.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@Autowired
	private Environment environment;
	
	@RequestMapping("javainfo")
	public String Info(){
		return environment.getProperty("JAVA_HOME");
	}
}
