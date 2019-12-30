package com.bobo.dubbo.consumer.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bobo.dubbo.bean.Student;
import com.bobo.dubbo.service.StudentService;

@RestController
public class StudentController {

	@Reference(version="1.0.0") 
	private StudentService studentService;
	
	@RequestMapping("/students/{id}")
	public Student getStudent(@PathVariable String id){
		
		return studentService.getStudent(id);
	}
}
