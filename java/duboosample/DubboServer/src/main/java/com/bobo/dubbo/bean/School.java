package com.bobo.dubbo.bean;

import java.io.Serializable;

public class School implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8780642038157822823L;
	private String id;
	private String name;
	private String address;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
