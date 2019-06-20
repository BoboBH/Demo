package com.bobo.fristsba.test.service;

import java.util.ArrayList;
import java.util.Optional;

import org.jboss.jandex.Main;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bobo.fristsba.FristsbaApplication;
import com.bobo.fristsba.domain.Student;
import com.bobo.fristsba.service.StudentRepository;
import com.bobo.fristsba.service.StudentService;

@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@ComponentScan("com.bobo.fristsba.service")
@EnableJpaRepositories("com.bobo.fristsba.service")
//@EntityScan("com.bobo.fristsba.domain")//@DataJpaTest

@SpringBootTest(classes=FristsbaApplication.class)
public class StudentRepositoryTest {

	
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Test
	public void test() throws Exception{
		/* Unit Test can not inject Repository */
		Optional<Student> existSt = studentRepository.findById("10000");
		if(existSt.isPresent())
			studentRepository.delete(existSt.get());
		Student st = new Student();
		st.setId("10000");
		st.setName("Bobo Huang");
		studentRepository.save(st);
		Optional<Student> dbSt = studentRepository.findById("10000");
		Assert.assertEquals(st.getId(), dbSt.get().getId());
		ArrayList<Student> list  = studentRepository.findByName("bobo huang");
		Assert.assertNotNull(list);
		
	}
}
