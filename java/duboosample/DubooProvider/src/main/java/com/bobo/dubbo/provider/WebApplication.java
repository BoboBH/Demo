
package com.bobo.dubbo.provider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.bobo.dubbo.provider.WebApplication;

@RestController
@SpringBootApplication
@ComponentScan(basePackages={"com.bobo.duboo.provider.service","com.bobo.dubbo.provider.controller"})
@EnableDubbo
@DubboComponentScan(basePackages={"com.bobo.duboo.provider.service","com.bobo.dubbo.school.service"})
@EnableHystrix
public class WebApplication {
	public static void main(String[] args){
        System.setProperty("dubbo.application.logger", "slf4j");
		SpringApplication.run(WebApplication.class, args);
	}
	
	@RequestMapping("/hello")
	public String home(){
		return "Hello world, Welcome visit Dubbo Provider!";
	}
}
