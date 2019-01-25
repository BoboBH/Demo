package com.bobo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.bean.TestUser;

@RestController
@ComponentScan("com.bobo.bean")
public class TestUserController {

	@Autowired
	private TestUser testUser;
	
	@RequestMapping(value="/test", method = RequestMethod.GET)
	public String test(){
		return testUser.toString();
	}
}
