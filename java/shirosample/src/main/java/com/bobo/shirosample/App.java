package com.bobo.shirosample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
	final static Logger log = LoggerFactory.getLogger(App.class);
    public static void main(String[] args )
    {
    	log.info("-------------------------Shiro Sample Start-------------------------");
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class, args);
    	log.info("-------------------------Shiro Sample End-------------------------");
    }
}
