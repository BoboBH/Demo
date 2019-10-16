package com.bobo.fristsba.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bobo.fristsba.authentication.JWTToken;
import com.bobo.fristsba.authentication.TokenInfo;
import com.bobo.fristsba.authentication.TokenUtil;
import com.bobo.fristsba.domain.Role;
import com.bobo.fristsba.domain.User;
import com.bobo.fristsba.mapper.RoleMapper;
import com.bobo.fristsba.mapper.UserMapper;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
public class StudentController {

	@Autowired
	private UserMapper userMapper;
	@Autowired 
	private RoleMapper roleMapper;
   @RequestMapping("/")
    public String index() {
        return "redirect:/index";
    }
   
   @RequestMapping("/index")
   public String index(Model model) {
       return "index";
   }
	

	@RequestMapping(value="login",method={RequestMethod.GET})
	public String login(){
		return "login";
	}


	@RequestMapping(value="logoutm")	
	public String logout(){
		try{
			SecurityUtils.getSubject().logout();
		}
		catch(Exception ex){
			return "login";
		}
		return "login";
	}
	
	@RequestMapping(value="authorize",method={RequestMethod.POST})	
	public String login(Model model, String username, String password){
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		User dbuser = userMapper.getUserByNameAndPassword(user);
		if(dbuser == null){
			return "login";
		}
		List<Role> roles = roleMapper.getRolesByUserId(dbuser.getId());
		String roleNames = roles.stream().map(r->r.getName()).collect(Collectors.joining(","));
		String token = TokenUtil.sign(username, password,dbuser.getId(),roleNames);
		JWTToken jwtToken = new JWTToken(token);
		//UsernamePasswordToken toekn = new UsernamePasswordToken(username, password);
		try{
			SecurityUtils.getSubject().login(jwtToken);
		}
		catch(Exception ex){
			return "login";
		}
		model.addAttribute("username",username);
		return "index";
	}
}
