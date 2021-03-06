package com.bobo.spring_boot_app;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.*;

import com.bobo.config.ApplicationConfig;
import com.bobo.mybatis.bean.UserMessage;
import com.bobo.service.UserMessageService;

/**
 * Hello world!
 *
 */
@RestController
@EnableAutoConfiguration
@SpringBootConfiguration
@ComponentScan(value="com.bobo.config,com.bobo.mybatis.bean,com.bobo.service,com.bobo.controller,org.springframework.data.redis.core")
@MapperScan("com.bobo.mybatis.mapper")//告诉mapper所在的包名
@EnableCaching
public class App 
{
	@Autowired
	protected ApplicationConfig applicationConfig;
    public static void main( String[] args )
    {
        System.out.println( "Start Spring Boot!" );
        ApplicationContext annotationContext = new ClassPathXmlApplicationContext("SpringBeans.xml");  
        ApplicationConfig config = (ApplicationConfig)annotationContext.getBean("applicationConfig");
        System.out.println(config.getApplicationName());
        SpringApplication.run(App.class, args);
    }
    
    @RequestMapping("/")
    String home() {
    	System.out.println(applicationConfig.getApplicationName());
    	ApplicationContext annotationContext = new ClassPathXmlApplicationContext("SpringBeans.xml");  
        ApplicationConfig config = (ApplicationConfig)annotationContext.getBean("applicationConfig");
        System.out.println(config.getApplicationName());
        return "Hello Strping Boot!";
    }
    
    @Resource
	private UserMessageService userMessageService;
	
	@RequestMapping("userMessage/{id}")
	public List<UserMessage> getUserMessageById(@PathVariable("id") int id){
		return  this.userMessageService.getUserMessageById(id);
	}
	
	@RequestMapping(value="userMessage", method=RequestMethod.POST)
	public String addUserMessageById(@RequestBody UserMessage userMessage)
	{
		this.userMessageService.save(userMessage);
		return "Success";
	}
}
