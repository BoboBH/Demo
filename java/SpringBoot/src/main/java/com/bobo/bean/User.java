package com.bobo.bean;
 
import java.io.Serializable;
import java.util.Date;
 
/**
 * @ClassName cn.saytime.bean.User
 * @Description
 * @date 2017-07-04 22:47:28
 */
public class User implements Serializable{
 
	private int id;
	private String username;
	private int age;
	private Date ctm;
	private Integer grade;
	public User() {
	}
 
	public User(String username, int age) {
		this.username = username;
		this.age = age;
		this.ctm = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getCtm() {
		return ctm;
	}

	public void setCtm(Date ctm) {
		this.ctm = ctm;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
 
}
