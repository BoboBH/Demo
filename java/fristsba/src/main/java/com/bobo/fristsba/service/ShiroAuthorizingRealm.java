package com.bobo.fristsba.service;


import java.util.List;

import javax.validation.constraints.Null;

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
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bobo.fristsba.authentication.JWTToken;
import com.bobo.fristsba.authentication.TokenUtil;
import com.bobo.fristsba.domain.Role;
import com.bobo.fristsba.domain.User;
import com.bobo.fristsba.mapper.RoleMapper;
import com.bobo.fristsba.mapper.UserMapper;

import io.netty.util.internal.StringUtil;

/**
 * 
 * @author bobo.huang
 * @update 2019-09-25
 * @desc support jwt on shiro
 */
public class ShiroAuthorizingRealm extends AuthorizingRealm 
{
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	private static String USER_SESSION_KEY = "USER_SESSION_KEY";	
	
	@Override
	public boolean supports(AuthenticationToken token) {
		if(token instanceof JWTToken)
			return true;
		return false;
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pricipals)
	{
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		String username = String.valueOf(pricipals.getPrimaryPrincipal());
		boolean isJWTPrincipal = false;
		try{
			//Take role from header instead of retrieve from database;
			 DecodedJWT jwt = JWT.decode(username);
			 String role = jwt.getClaim(TokenUtil.ROLE_CLAIM).asString();
			 if(!StringUtil.isNullOrEmpty(role)){
				 String[] roles = role.split(",");
				 for (String r : roles) {
					authorizationInfo.addRole(r);
				}
			 }
		}
		catch(JWTDecodeException ex){
			isJWTPrincipal = true;
		}
		if(!isJWTPrincipal && !StringUtil.isNullOrEmpty(username)){			
			List<User> users = userMapper.getUserByName(username);
			for (User user : users) {
				List<Role> roles = roleMapper.getRolesByUserId(user.getId());
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
		String strtoken = (String)token.getCredentials();
		String username = TokenUtil.getUsername(strtoken);
		if(StringUtil.isNullOrEmpty(username))
			throw new AuthenticationException("Invalid Token");
		List<User> users = userMapper.getUserByName(username);
		User loginUser = null;
		for (User user : users) {
			if(TokenUtil.verify(strtoken, username, user.getPassword())){
				loginUser = user;
				break;
			}
		}
		if(loginUser == null)
			throw new AuthenticationException("User does not exist or invalid password");
		return new SimpleAuthenticationInfo(strtoken, strtoken, this.getName());//principal and credential must be matched with token;
		/*UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String userName = upToken.getUsername();
		String password = String.valueOf(upToken.getPassword());		
		List<User> users = userMapper.getUserByName(userName);
		User loginUser = null;
		for (User u : users) {
			if(password.equals(u.getPassword()))
			{
				loginUser = u;
				break;
			}
		}
		if(loginUser == null){
            throw new AuthenticationException("用户名或密码错误.");
		}
		org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
		org.apache.shiro.session.Session session = currentUser.getSession();
		//session.setAttribute(USER_SESSION_KEY, user);
		return new SimpleAuthenticationInfo(userName, loginUser.getPassword(), this.getName());*/
	}


}
