package com.bobo.shirosample.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.shirosample.auth.JwtToken;
import com.bobo.shirosample.auth.TokenUtil;
import com.bobo.shirosample.bean.User;
import com.bobo.shirosample.service.UserService;

@RestController
@RequestMapping("api")
public class LoginController {

	@Autowired
	private UserService userService;
	@RequestMapping(value="login", method = {RequestMethod.POST})
	public String login(@RequestBody User user){
		User dbUser = userService.getUser(user.getUsername());
		if(dbUser == null || !dbUser.getPassword().equals(user.getPassword()))
			return "Username or password is invalid";
		Subject subject = SecurityUtils.getSubject();
		List<String> roles = userService.getRoles(user.getUsername());
		String strRoles =  roles.stream().collect(Collectors.joining(","));
		String stoken = TokenUtil.sign(user.getUsername(), user.getPassword(), user.getUsername(),  strRoles);
		JwtToken jwtToken = new JwtToken(stoken);
		//UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
		try{
			subject.login(jwtToken);
		}
		catch(AuthenticationException e){
			e.printStackTrace();
			return "No Authentication";
		}
		catch (Exception e) {
			return "Username or password is invalid";
		}
		return stoken;
	}
	
}
