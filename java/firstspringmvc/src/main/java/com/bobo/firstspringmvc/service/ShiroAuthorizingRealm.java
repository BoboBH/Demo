package com.bobo.firstspringmvc.service;


import java.util.List;

import org.apache.ibatis.javassist.bytecode.ConstantAttribute;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.bobo.firstspringmvc.bean.Role;
import com.bobo.firstspringmvc.bean.User;
import com.bobo.firstspringmvc.mapper.RoleMapper;
import com.bobo.firstspringmvc.mapper.UserMapper;

public class ShiroAuthorizingRealm extends AuthorizingRealm 
{
	private static String USER_SESSION_KEY = "USER_SESSION_KEY";
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pricipals)
	{
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		String username = String.valueOf(pricipals.getPrimaryPrincipal());
		User user = userMapper.getUserByUsername(username);
		if(user != null){
			List<Role> roles = roleMapper.getRolesByUserId(user.getId().toString());
			if(roles != null && roles.size() > 0){
				for (Role role : roles) {
					authorizationInfo.addRole(role.getName());
				}
			}
		}
		return authorizationInfo;
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) 
			throws AuthenticationException
	{
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String userName = upToken.getUsername();
		String password = String.valueOf(upToken.getPassword());
		User user = userMapper.getUserByUsername(userName);
		if(user == null || !user.getPassword().equals(password)){
            throw new AuthenticationException("用户名或密码错误.");
		}
		org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
		org.apache.shiro.session.Session session = currentUser.getSession();
		session.setAttribute(USER_SESSION_KEY, user);
		return new SimpleAuthenticationInfo(userName, password,this.getName());
	}


}
