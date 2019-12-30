package com.bobo.zktest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
	@RequestMapping("sayhello")
	public String sayHello(String name){
		return "Hello ".concat(name).concat("!");
	}
}
