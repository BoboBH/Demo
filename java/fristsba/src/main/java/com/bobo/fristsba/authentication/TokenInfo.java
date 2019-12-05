package com.bobo.fristsba.authentication;

import java.io.Serializable;


public class TokenInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String token;
	private String message;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public TokenInfo(){
		
	}
	
	public TokenInfo(String id, String token){
		this.id = id;
		this.token = token;
	}
	
	public TokenInfo(String message)
	{
		this.message = message;
	}

}
