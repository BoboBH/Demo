package com.bobo.simple;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import simplecommon.Version;

@RestController
public class HomeController {
	
	@RequestMapping("/")
	public String home(){
		return "Welcome Visit Simple Web Site";
	}
	
	@RequestMapping("/version")
	public Version version(){
		Version version = new Version();
		version.setVersion("0.1");
		return version;
	}


}
