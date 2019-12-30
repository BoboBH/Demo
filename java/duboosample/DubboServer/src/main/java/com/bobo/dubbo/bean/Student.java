package com.bobo.dubbo.bean;
import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;


public class Student implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8495383139491916275L;
	private String id;
	private String name;
	private String address;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date dob;
	private Integer age;
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
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}
