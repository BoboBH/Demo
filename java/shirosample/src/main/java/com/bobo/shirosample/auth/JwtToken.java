package com.bobo.shirosample.auth;


import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7500166458995002925L;
	public String token;
	
	public JwtToken(String token){
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
