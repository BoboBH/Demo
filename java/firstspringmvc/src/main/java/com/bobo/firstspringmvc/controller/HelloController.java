package com.bobo.firstspringmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.bobo.firstspringmvc.bean.School;
import com.bobo.firstspringmvc.bean.Student;
@EnableWebMvc
@Controller
public class HelloController {

	private static String MESSAGE = "Welcome to Sprign MVC";
	
	@RequestMapping("hello")
	public ModelAndView showMessage(@RequestParam(value="name", required=false,defaultValue="Spring") String name){
		ModelAndView mv = new ModelAndView("hellospring");
		mv.addObject("message",MESSAGE);
		mv.addObject("name",name);
		return mv;
	}
	
	@RequestMapping(value="message", method = RequestMethod.GET)
	public @ResponseBody String getMessage(){
		return "Hello World";
	}
	@RequestMapping(value="student/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Student getStudent(@PathVariable String id){
		return new Student(id, "Bobo Huang", 18);
	}
	

	@RequestMapping(value="school/{id}", method = RequestMethod.GET)
	@ResponseBody
	public School getSchool(@PathVariable String id){
		School school = new School();
		school.setId(id);
		school.setName("3rd School Of Hanchan");
		return school;
	}
}
