package com.bobo.sb_no_web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.bobo.sb_no_web.config.UCConfig;
/***
 * 
 * @author bobo.huang
 * Do not start Web container, just like a general console application;
 */
@SpringBootApplication
public class SbNoWebApplication  implements CommandLineRunner{

	@Autowired
	private UCConfig ucConfig;
	public static void main(String[] args) {
		//SpringApplication.run(SbNoWebApplication.class, args);
		new SpringApplicationBuilder(SbNoWebApplication.class)
	    .web(WebApplicationType.NONE)
	    .run(args);
	}

	@Override
	public void run(String[] args){
		System.out.println("UC Config Info:");
		System.out.print("Base URL:");
		System.out.println(ucConfig.getBaseUrl());
		System.out.print("Terminal Key:");
		System.out.println(ucConfig.getBaseUrl());
	}
}
