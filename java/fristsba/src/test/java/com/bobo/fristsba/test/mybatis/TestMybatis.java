package com.bobo.fristsba.test.mybatis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bobo.fristsba.FristsbaApplication;
import com.bobo.fristsba.domain.Student;
import com.bobo.fristsba.mapper.StudentMapper;



@SpringBootApplication
@MapperScan("com.bobo.fristsba.mapper")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@EnableJpaRepositories("com.bobo.fristsba.service")
@SpringBootTest(classes=FristsbaApplication.class)
public class TestMybatis {
	
	@Autowired
	private StudentMapper studentMapper;
	@Test
	public void testInsertStudent(){
		
		String id = "10001";
		Student existSt = studentMapper.getStudentById(id);
		if(existSt != null)
			studentMapper.delete(existSt.getId());
		Student st = new Student();
		st.setId(id);
		st.setName("Happy Huang");
		studentMapper.insert(st);
		existSt = studentMapper.getStudentById(id);
		Assert.assertNotNull(existSt);
		st.setName("Huang Happy");
		studentMapper.update(st);
		existSt = studentMapper.getStudentById(id);
		Assert.assertNotNull(existSt);
		Assert.assertEquals("Huang Happy", existSt.getName());
	}

}
