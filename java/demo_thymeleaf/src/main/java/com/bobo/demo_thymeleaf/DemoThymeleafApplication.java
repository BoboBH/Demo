package com.bobo.demo_thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DemoThymeleafApplication  extends SpringBootServletInitializer {

	  @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(DemoThymeleafApplication.class);
	    }
	public static void main(String[] args) {
		SpringApplication.run(DemoThymeleafApplication.class, args);
	}

}
