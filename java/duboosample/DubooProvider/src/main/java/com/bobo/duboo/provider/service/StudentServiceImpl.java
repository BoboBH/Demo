package com.bobo.duboo.provider.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

//import com.alibaba.dubbo.config.annotation.Service;
import com.bobo.dubbo.bean.Student;
import com.bobo.dubbo.service.StudentService;
//@Service(interfaceClass=StudentService.class)
@com.alibaba.dubbo.config.annotation.Service(version="1.0.0",interfaceClass=StudentService.class)
@Service("studentService")
public class StudentServiceImpl implements StudentService {

	private final static Logger logger= LoggerFactory.getLogger(StudentServiceImpl.class);
	@Override
	public Student getStudent(String id) {
		logger.info("get student info by id({})", id);
		Student student = new Student();
		student.setId(id);
		student.setName("Bobo Huang");
		student.setAddress("阳光花园17-2403");
		student.setAge(40);
		try {
			student.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1981-01-01"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("got student info by id({})", id);
		return student;
	}

	@Override
	public boolean saveStudent(Student student) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean deleteStudent(String id) {
		// TODO Auto-generated method stub
		return true;
	}

}
