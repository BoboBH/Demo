package com.bobo.bean;

import java.io.Serializable;

public class RedisUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private int id;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public RedisUser(int id, String username){
		this.userName = username;
		this.id = id;
	}
}
