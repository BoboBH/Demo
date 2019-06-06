package com.bobo.fristsba.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value="classpath:application.properties",encoding = "UTF-8")
public class NeoProperties {
	@Value("${com.neo.title}")
	private String title;
	@Value("${com.neo.description}")
	private String description;
	
	public String getTitle(){
		return this.title;
	}
	public String getDescription(){
		return this.description;
	}
	public void SetTitle(String value){
		this.title = value;
	}
	public void SetDescription(String value){
		this.description = value;
	}
}
