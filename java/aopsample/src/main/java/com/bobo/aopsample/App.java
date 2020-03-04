package com.bobo.aopsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application Entrance!
 *
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
    	System.out.println( "------------------------AOP Sample Start----------------------------" );
    	SpringApplication.run(App.class, args);
    	System.out.println( "------------------------AOP Sample End----------------------------" );
    }
}
