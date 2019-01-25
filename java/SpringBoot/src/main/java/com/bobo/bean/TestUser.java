package com.bobo.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value="classpath:test.user.yml")
@ConfigurationProperties(prefix = "test.user")
public class TestUser {

	@Value("${username}")
	private String username;
	@Value("${age}")
	private int age;
	
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
	
	public String toString(){
		return String.format("The age of %s is %d", this.getUsername(), this.getAge());
	}
}
