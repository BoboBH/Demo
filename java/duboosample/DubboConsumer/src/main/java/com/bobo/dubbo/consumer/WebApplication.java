package com.bobo.dubbo.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.bobo.dubbo.consumer.WebApplication;
@EnableDubbo
@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.bobo.dubbo.consumer.controller"})
@EnableHystrix
public class WebApplication 
{
	@RequestMapping("/")
	public String home(){
		return "Hello world, Welcome visit Dubbo Consumer!";
	}
	public static void main(String[] args){
		SpringApplication.run(WebApplication.class, args);
	}
}