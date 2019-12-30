package com.bobo.zktest.bean.elasticsearch;

import java.io.Serializable;

public class Department implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8866824700665630370L;
	private String id;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
