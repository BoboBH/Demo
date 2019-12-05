package com.bobo.fristsba.authentication;

import org.apache.shiro.authc.AuthenticationToken;

import net.bytebuddy.asm.Advice.This;

/*
 * @author Bobo Huang
 * @create 2019-9-25
 * @desc * 验证Springboot + Shiro + JWT
 */
public class JWTToken implements AuthenticationToken{
	private String token;
	public JWTToken(String token){
		this.token = token;
	}
	
	@Override
	public Object getPrincipal(){
		return this.token;
	}
	
	@Override 
	public java.lang.Object getCredentials() {
		return this.token;
	}

}
