package com.bobo.fristsba.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bobo.fristsba.domain.Student;

public interface StudentRepository extends JpaRepository<Student,String>{
	
	Student findByName(String name);
}
