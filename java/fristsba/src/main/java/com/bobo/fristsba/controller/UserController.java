package com.bobo.fristsba.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.fristsba.domain.User;

@RestController
public class UserController {
	
	@RequestMapping(value="login",method={RequestMethod.POST})	
	@ResponseBody
	public String login(@RequestBody User user){
		UsernamePasswordToken toekn = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		toekn.setRememberMe(user.isRememberme());
		try{
			SecurityUtils.getSubject().login(toekn);
		}
		catch(Exception ex){
			return "";
		}
		return user.getUsername();
	}
	
	@RequestMapping("admin/message")
	@ResponseBody
	public String getAdminMessage(){
		return "Logined with Admin role";
	}

	@RequestMapping("ops/message")
	@ResponseBody
	public String getOpsMessage(){
		return "Logined with Ops role";
	}

}
