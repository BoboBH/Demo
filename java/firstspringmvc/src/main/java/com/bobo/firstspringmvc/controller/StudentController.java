package com.bobo.firstspringmvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.bobo.firstspringmvc.bean.Student;
import com.bobo.firstspringmvc.service.IStudentService;
@EnableWebMvc
@Controller
public class StudentController {

	@Autowired IStudentService studentService;
	@RequestMapping(value="/student",method=RequestMethod.GET)  
    public String test(HttpServletRequest request,Model model){  
        model.addAttribute("students", studentService.getAllStudent());  
        return "studentlist";  
    }  
	
	@RequestMapping(value="/api/student",method=RequestMethod.GET)
	@ResponseBody
	public List<Student> getAllStudent(){
		return studentService.getAllStudent();
	}
}
