package org.cloudconfig2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@RequestMapping("/")
	public String home(){
		return "Welcome Visit Cloud Config 2";
	}
	
	@RequestMapping("/hello")
	public String hello(){
		return "Hello Cloud Config 2";
	}
}
