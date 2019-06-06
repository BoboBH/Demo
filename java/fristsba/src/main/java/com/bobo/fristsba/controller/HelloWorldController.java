/**
 * 
 */
/**
 * @author bobo.huang
 *
 */
package com.bobo.fristsba.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.fristsba.config.NeoProperties;
import com.bobo.fristsba.domain.Student;

@RestController
public class HelloWorldController{
	
	@Autowired
	private NeoProperties neoConfig;
	@RequestMapping("hello")
	public String index(){
		return "Hello Bobo Huang";
	}
	@RequestMapping("neoconfig")
	public NeoProperties getConfig(){
		return this.neoConfig;
	}
	@RequestMapping("students")
	public ArrayList<Student> getAllStudent(){
		
		ArrayList<Student> list = new ArrayList<Student>();
		Student st1 = new Student();
		st1.setId("1");
		st1.setName("bobo Huang");
		Student st2 = new Student();
		st2.setId("2");
		st2.setName("黄晓乐");
		list.add(st1);
		list.add(st2);
		return list;
	}
	
}