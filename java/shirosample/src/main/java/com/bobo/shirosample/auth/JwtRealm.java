package com.bobo.shirosample.auth;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.bobo.shirosample.bean.User;
import com.bobo.shirosample.service.UserService;


public class JwtRealm extends AuthorizingRealm{

	final static Logger log = LoggerFactory.getLogger(AuthorizingRealm.class);
	
	@Autowired
	private UserService userService;
	
	public JwtRealm(UserService userService){
		this.userService = userService;
	}
	@Override
	public boolean supports(AuthenticationToken token) {
		if(token instanceof JwtToken)
			return true;
		log.error("does not support" +  token.getClass().getName());
		return false;
	}
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo  simpleAuthorizationInfo  = new SimpleAuthorizationInfo ();
		String token = (String)principals.getPrimaryPrincipal();
		String userName = TokenUtil.getUsername(token);
		List<String> roles = userService.getRoles(userName);
		for (String role : roles) {
			simpleAuthorizationInfo.addRole(role);
		}
		return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		String jwt = (String) token.getPrincipal();
		if(jwt == null)
			throw new NullPointerException("Jwt Token does not exist");
		String username = TokenUtil.getUsername(jwt);
		if(StringUtils.isEmpty(username))
			throw new UnauthenticatedException(); 
		User user = userService.getUser(username);
		if(user == null)
			throw new UnauthenticatedException(); 
		if(!TokenUtil.verify(jwt, user.getUsername(),user.getPassword()))
			throw new UnknownAccountException();
		log.info("{} logined with Token", username);		
		return new SimpleAuthenticationInfo(jwt,jwt,this.getName());
	}
	

}
