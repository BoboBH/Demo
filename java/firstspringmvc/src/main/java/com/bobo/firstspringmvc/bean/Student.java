package com.bobo.firstspringmvc.bean;

import java.io.Serializable;

public class Student implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private Integer age;
	public String getId() {
		return id;
	}
	public Student(){
		
	}
	public Student(String id, String name, Integer age){
		this.id = id;
		this.name = name;
		this.age = age;
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
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

}
