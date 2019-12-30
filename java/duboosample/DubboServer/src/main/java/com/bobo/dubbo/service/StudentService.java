package com.bobo.dubbo.service;

import com.bobo.dubbo.bean.Student;

public interface StudentService {
	Student getStudent(String id);
	boolean saveStudent(Student student);
	boolean deleteStudent(String id);
}
