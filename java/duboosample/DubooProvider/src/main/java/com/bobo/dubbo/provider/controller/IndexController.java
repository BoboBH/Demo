package com.bobo.dubbo.provider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@RequestMapping("/")
	public String home(){
		return "Hello world, Welcome visit Dubbo Provider!";
	}
}
