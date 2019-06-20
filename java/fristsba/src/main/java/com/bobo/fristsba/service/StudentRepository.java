package com.bobo.fristsba.service;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bobo.fristsba.domain.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student,String>{
	
	ArrayList<Student> findByName(String name);
}
