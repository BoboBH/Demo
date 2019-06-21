package com.bobo.firstspringmvc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bobo.firstspringmvc.bean.Student;
import com.bobo.firstspringmvc.mapper.StudentMapper;
import com.bobo.firstspringmvc.service.IStudentService;
@Service
public class StudentServiceImpl implements IStudentService {

	@Autowired StudentMapper studentMapper;
	public Student getStudentById(String id){
		return studentMapper.getStudentById(id);
	}
	public List<Student> getAllStudent(){
		return studentMapper.getAllStudent();
	}
}
