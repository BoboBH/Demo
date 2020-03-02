package com.bobo.shirosample.auth;

import java.util.List;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.bobo.shirosample.bean.User;
import com.bobo.shirosample.service.UserService;

import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
/***
 * 
 * @author bobo.huang
 * @Description:
 *    It is only applicable for Shiro Authorize. It is deprecated once integrate JwtRealm.
 */
public class CustomRealm extends AuthorizingRealm{

	public CustomRealm(UserService userService){
		this.userService = userService;
	}
	private UserService userService;
	@Override
	protected  SimpleAuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection){

		SimpleAuthorizationInfo  simpleAuthorizationInfo  = new SimpleAuthorizationInfo ();
		String name = (String)principalCollection.getPrimaryPrincipal();
		List<String> roles = userService.getRoles(name);
		for (String role : roles) {
			simpleAuthorizationInfo.addRole(role);
		}
		return simpleAuthorizationInfo;
	}
	
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) 
			throws AuthenticationException{
		if(token.getPrincipal() == null)
			return null;
		User user = null;
		if(token instanceof UsernamePasswordToken){
			UsernamePasswordToken t = (UsernamePasswordToken)token;
			user = userService.getUser(t.getUsername());
			if(user == null || !user.getPassword().equals(String.valueOf(t.getPassword())))
				user = null;
		}
		if(user == null)
			return null;
		SimpleAuthenticationInfo  authorizationInfo = new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), this.getName());		
		return authorizationInfo;
	}
}
