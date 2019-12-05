package com.bobo.fristsba.domain;

import javax.persistence.Column;

public class SubStudent {
	
	@Column(name="id")
	private String id;
	@Column(name="name")
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
