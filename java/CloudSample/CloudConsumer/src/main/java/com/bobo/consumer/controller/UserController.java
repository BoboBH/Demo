package com.bobo.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.cloudmodule.bean.ResponseUser;
import com.bobo.cloudmodule.bean.User;
import com.bobo.cloudmodule.client.UserFeignClient;


@RequestMapping(value="user")
@RestController
public class UserController {

	@Autowired
	private UserFeignClient userFeignClient;	
	
	@RequestMapping(value="get", method=RequestMethod.GET)
	public ResponseUser getUser(@Validated Integer id){	
		return userFeignClient.findById(id);
	}
	

	@RequestMapping(value="add", method=RequestMethod.POST)
	public ResponseUser addUser(@RequestBody User user){		
		return userFeignClient.addUser(user);
	}
	
}
