package com.bobo.fristsba.test.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bobo.fristsba.domain.Student;
import com.bobo.fristsba.service.StudentRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@WebAppConfiguration
//@ComponentScan("com.bobo.fristsba.service")
@EnableJpaRepositories("com.bobo.fristsba.service")
//@EntityScan("com.bobo.fristsba.domain")
public class StudentRepositoryTest {

	@Autowired
	private StudentRepository studentRepository;
	
	@Test
	public void test() throws Exception{
		Student st = new Student();
		st.setId("10000");
		st.setName("Bobo Huang");
		studentRepository.save(st);
		Optional<Student> dbSt = studentRepository.findById("10000");
		Assert.assertEquals(st.getId(), dbSt.get().getId());
	}
}
