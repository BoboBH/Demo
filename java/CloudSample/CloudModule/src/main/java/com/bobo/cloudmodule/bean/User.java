package com.bobo.cloudmodule.bean;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5223368753560925245L;
	private Integer id;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public User(){
		
	}
	
	public User(Integer id, String name){
		this();
		this.id = id;
		this.name = name;
	}
}
