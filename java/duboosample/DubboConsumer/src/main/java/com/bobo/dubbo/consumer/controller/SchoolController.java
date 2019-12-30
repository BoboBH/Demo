package com.bobo.dubbo.consumer.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bobo.dubbo.bean.School;
import com.bobo.dubbo.service.SchoolService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class SchoolController {

	@Reference(version="1.0.0",check=false)
	private SchoolService schoolService;
	
	@HystrixCommand(fallbackMethod = "getErrorSchool")
	@RequestMapping("/schools/{id}")
	public School getSchool(@PathVariable String id){
		return schoolService.getSchool(id);
	}
	
	public School getErrorSchool(String id){
		School school = new School();
		school.setId("Error");
		return school;
	}
}
