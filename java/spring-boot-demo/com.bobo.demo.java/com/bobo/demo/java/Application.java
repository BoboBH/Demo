package com.bobo.demo.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.demo.java.config.ApplicationConfig;


@SpringBootApplication
@Configuration
public class Application {

	//@Autowired
	//public ApplicationConfig applicationConfig;
	  @RequestMapping("/home")
	    public String greeting() {
		    //String appName= applicationConfig.getAppName();
		    //System.out.println(appName);
		  	//System.out.println(applicationConfig.getServerPort());
	        return "Hello World Srping Boot!";
	    }
	  
	  @RequestMapping("/error")
	    public String error() {
	        return "Srping Boot occur a system error!";
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(Application.class, args);
	    }
	    

}
