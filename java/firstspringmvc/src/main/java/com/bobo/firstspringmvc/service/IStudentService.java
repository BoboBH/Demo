package com.bobo.firstspringmvc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bobo.firstspringmvc.bean.Student;
@Service
public interface IStudentService {

	public Student getStudentById(String id);
	public List<Student>  getAllStudent();
}
