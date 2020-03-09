package com.bobo.aopsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import com.bobo.aopsample.bean.Student;

/**
 * Application Entrance!
 * 
 *
 */
@SpringBootApplication
@EnableConfigurationProperties(Student.class)
@RefreshScope
public class App 
{
    public static void main( String[] args )
    {
    	System.out.println( "------------------------AOP Sample Start----------------------------" );
    	SpringApplication.run(App.class, args);
    	System.out.println( "------------------------AOP Sample End----------------------------" );
    }
}
