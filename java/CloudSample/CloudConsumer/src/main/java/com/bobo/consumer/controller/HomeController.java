package com.bobo.consumer.controller;

import org.CloudConsumer.EurekoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@Autowired
	private EurekoConfig config;
	@RequestMapping("/")
	public String home(){
		return "Welcome visit Cloud Consumer";
	}
	
	@RequestMapping("/config")
	public EurekoConfig config(){
		return config;
	}
}
