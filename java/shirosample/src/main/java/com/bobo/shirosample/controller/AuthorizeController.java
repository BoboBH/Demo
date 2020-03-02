package com.bobo.shirosample.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bobo.shirosample.auth.JwtToken;
import com.bobo.shirosample.auth.TokenUtil;
import com.bobo.shirosample.bean.User;
import com.bobo.shirosample.service.UserService;


@Controller
public class AuthorizeController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/authorize", method = {RequestMethod.POST})
	public String login(Model model, String username, String password){
		User dbUser = userService.getUser(username);
		if(dbUser == null || !dbUser.getPassword().equals(password))
			return "Username or password is invalid";
		Subject subject = SecurityUtils.getSubject();
		List<String> roles = userService.getRoles(dbUser.getUsername());
		String strRoles =  roles.stream().collect(Collectors.joining(","));
		String stoken = TokenUtil.sign(dbUser.getUsername(), dbUser.getPassword(), dbUser.getUsername(),  strRoles);
		JwtToken jwtToken = new JwtToken(stoken);	
		try{
			subject.login(jwtToken);
		}
		catch(AuthenticationException e){
			e.printStackTrace();
			model.addAttribute("ErrorMessage", "No Authentication") ;
			return "login";
		}
		catch (Exception e) {

			model.addAttribute("ErrorMessage", "Username or password is invalid") ;
			return "login";
		}
		return "index";
	}
	
	@RequestMapping(value="/login", method = {RequestMethod.GET})
	public String login(){
		return "login";
	}

	@RequestMapping(value="/index", method = {RequestMethod.GET})
	public String index(){
		return "index";
	}
}
