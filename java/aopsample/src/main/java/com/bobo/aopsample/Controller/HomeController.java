package com.bobo.aopsample.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.aopsample.bean.Student;

@RestController
public class HomeController {

	@Autowired
	private Student student;
	@RequestMapping("/")
	public String home(){
		return "Welcome Visit AOP Sample";
	}
	
	@RequestMapping("/student")
	public Student getStudent(){		
		return student;
	}
}
