package org.cloudconfig2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer
@EnableDiscoveryClient
@EnableZuulProxy
@EnableHystrix
public class App 
{
    public static void main( String[] args )
    {
    	
        System.out.println( "-------------Cloud Config Start----------------------------------" );
        SpringApplication.run(App.class, args);
        System.out.println( "-------------Cloud Config End----------------------------------" );
    }
}
