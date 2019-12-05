package com.bobo.fristsba.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.fristsba.authentication.JWTToken;
import com.bobo.fristsba.authentication.TokenInfo;
import com.bobo.fristsba.authentication.TokenUtil;
import com.bobo.fristsba.authentication.UserLoginToken;
import com.bobo.fristsba.domain.Role;
import com.bobo.fristsba.domain.User;
import com.bobo.fristsba.domain.UserProfile;
import com.bobo.fristsba.mapper.RoleMapper;
import com.bobo.fristsba.mapper.UserMapper;
import com.bobo.fristsba.service.TokenService;

import antlr.PreservingFileWriter;

@RestController
@RequestMapping("api")
public class JWTUserController {

	@Autowired
	TokenService tokenService;
	@Autowired
	UserMapper userMapper;
	@Autowired
	RoleMapper roleMapper;
	@PostMapping("login")
	public TokenInfo login(@RequestBody User user){
		User dbuser = userMapper.getUserByNameAndPassword(user);
		if(dbuser == null){
			return new TokenInfo("User does not exist or password is not valid");
		}
		List<Role> roles = roleMapper.getRolesByUserId(dbuser.getId());
		String roleNames = roles.stream().map(r->r.getName()).collect(Collectors.joining(","));
		String token = TokenUtil.sign(dbuser.getUsername(), dbuser.getPassword(),dbuser.getId(), roleNames);
		JWTToken jwtToken = new JWTToken(token);
		try{
			SecurityUtils.getSubject().login(jwtToken);
			return new TokenInfo(dbuser.getId(), token);
		}
		catch(Exception ex){
			return new TokenInfo("User does not exist or password is not valid");
		}
		
		/*User dbuser = userMapper.getUserByNameAndPassword(user);
		if(dbuser == null){
			return new TokenInfo("User does not exist or password is not valid");
		}
		String token = tokenService.getToken(dbuser);
		return new TokenInfo(dbuser.getId(), token);*/
	}
	
	@GetMapping("message")
	@UserLoginToken
	public String getMessage(){
		return "You have logined ever";
	}
	

	@GetMapping("adminmessage")
	@UserLoginToken(role="admin")
	public String getAdminMessage(){
		return "You are an Admin";
	}

	@GetMapping("shiro/admin/message")
	@ResponseBody
	public String getShiroAdminMessage(){
		return "You are an Admin verified by shiro";
	}
	
	/**
	 * 通过JWT的UserLoginToken验证，需要登录的userId与请求数据的userId一致；
	 * @param id
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("user/{id}/profile")
	@UserLoginToken(needusermatched=true)
	public UserProfile profile(@PathVariable String id) throws ParseException{
		UserProfile profile = new UserProfile();
		profile.setId(id);
		profile.setName("bobo huang");
		profile.setAddress("Yangguang Garden 17-2403");
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		profile.setDateOfBirth(ft.parse("1980-11-22"));
		return profile;
	}
}
