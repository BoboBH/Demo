package com.bobo.cloudmodule.bean;

import java.io.Serializable;

public class ResponseUser extends JsonData<User> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2562767534115567779L;
	
	public ResponseUser(){
		super();
	}
	
	public ResponseUser(User user){
		super(user);
	}
	
	public ResponseUser(int code, String msg, User user){
		super(code, msg, user);
	}

}
