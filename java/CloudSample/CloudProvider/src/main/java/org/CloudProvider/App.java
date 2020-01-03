package org.CloudProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableHystrix
//@EnableFeignClients(basePackages={"com.bobo.cloudmodule.client"})
@ComponentScan(basePackages={"com.bobo.cloudprovider.controller"})
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "-------------Cloud Provider Start--------------------" );
        SpringApplication.run(App.class, args);
        System.out.println( "-------------Cloud Provider End--------------------" );
    }
}
