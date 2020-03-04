package org.CloudConfigClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "------------------Cloud Config Client Start------------------" );
        SpringApplication.run(App.class, args);
        System.out.println( "------------------Cloud Config Client End------------------" );
    }
}
