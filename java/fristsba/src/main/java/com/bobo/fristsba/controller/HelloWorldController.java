/**
 * 
 */
/**
 * @author bobo.huang
 *
 */
package com.bobo.fristsba.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.fristsba.FristsbaApplication;
import com.bobo.fristsba.config.NeoProperties;
import com.bobo.fristsba.domain.Student;
import com.bobo.fristsba.service.StudentRepository;

@RestController
public class HelloWorldController{
	

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HelloWorldController.class);

	   
	@Autowired
	private RedisLockRegistry redisLockRegistry;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private NeoProperties neoConfig;
	@RequestMapping("hello")
	public String index(){
		log.info("Index returns :Hello Bobo Huang");
		return "Hello Bobo Huang";
	}
	@RequestMapping("neoconfig")
	public NeoProperties getConfig(){
		return this.neoConfig;
	}
	@RequestMapping("students")
	public List<Student> getAllStudent(){			
		return studentRepository.findAll();
	}
	@RequestMapping("students/{id}")
	public Student getStudent(@PathVariable String id){		
		Optional<Student> result = studentRepository.findById(id);
		if(result.isPresent())
			return result.get();
		return null;
	}
	
	@GetMapping("test/lock")
	public String TestLock() throws InterruptedException{
		Lock lock = redisLockRegistry.obtain("lock");
		boolean b1 = lock.tryLock(3, TimeUnit.SECONDS);
		String result = "";
		if(b1)
			result = "Got Lock";
		else
			result =  "Could not get Lock";
		lock.unlock();
		return result;
	}
	
	
}