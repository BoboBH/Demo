package com.bobo.dubbo.provider.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.dubbo.bean.Student;
import com.bobo.dubbo.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {
	@Autowired
	private StudentService studentService;
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public Student getStudent(@PathVariable String id){
		return studentService.getStudent(id);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public boolean saveStudent(@RequestBody Student student){
		return studentService.saveStudent(student);
	}

	@RequestMapping(value="", method=RequestMethod.GET)
	public List<Student> getAllStudent(@PathVariable String id){
		List<Student> students = new ArrayList<Student>();
		Student student = new Student();
		student.setId("122345");
		student.setName("Bobo Huang");
		students.add(student);
		return students;
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.POST)
	public boolean deleteStudent(@PathVariable String id){
		return studentService.deleteStudent(id);
	}

}
