package com.bobo.dubbo.school.service;

import org.springframework.stereotype.Service;

import com.bobo.dubbo.bean.School;
import com.bobo.dubbo.service.SchoolService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;;

@com.alibaba.dubbo.config.annotation.Service(version="1.0.0", interfaceClass=SchoolService.class)
@Service
public class SchoolServiceImpl implements SchoolService {

	 @HystrixCommand(commandProperties = {
	            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
	            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
	    })
	@Override
	public School getSchool(String id) {	
		 throw new RuntimeException("Exception to show hystrix enabled");
		/*School school = new School();
		school.setId(id);
		school.setName("TEST");
		school.setAddress("TEST ADDRESS");
		return school;*/
	}

}
