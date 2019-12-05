package com.bobo.fristsba.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bobo.fristsba.FristsbaApplication;
import com.bobo.fristsba.domain.Student;
import com.bobo.fristsba.domain.SubStudent;
import com.bobo.fristsba.service.StudentRepository;
import com.bobo.fristsba.service.StudentService;
import com.jayway.jsonpath.Option;

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
		String name = "Bobo Huang";
		String id = "10000";
		Student st = new Student();
		st.setId(id);
		st.setName(name);
		studentRepository.save(st);
		Optional<Student> dbSt = studentRepository.findById(id);
		Assert.assertEquals(st.getId(), dbSt.get().getId());
		ArrayList<Student> list  = studentRepository.findByName(name);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
		list = studentRepository.findByNameWithSql(name);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size()> 0);
		Optional<Student> result = list.stream().filter(s->s.getId().equals(id)).findFirst();
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.get());
		Assert.assertEquals(name, result.get().getName());
		

		Student sample = new Student();
		sample.setName(name);
		Example<Student> example = Example.of(sample);
		List<Student> stResult = studentRepository.findAll(example);
		Assert.assertNotNull(stResult);
		Assert.assertTrue(stResult.size() > 0);
		
		
		List<String> idList = studentRepository.findIdsByName(name);
		Optional<String> idResult = idList.stream().filter(s->s.equals(id)).findFirst();
		Assert.assertEquals(id, idResult.get());
		List<Object> sList = studentRepository.findObjectByName(name);
		String findName = null;
		for (Object object : sList) {
			Object[] arrayObj = (Object[]) object;
			findName=(String)arrayObj[1];
		}
		Assert.assertEquals(name, findName);
		
		
		/* Pagable and sort by id
		 */
		Sort sort = new Sort(Direction.ASC, "id");
		Page<Student> pageResult = studentRepository.findPageStudent(name, PageRequest.of(0, 1, sort));
		Assert.assertEquals(1, pageResult.getSize());
		st = pageResult.getContent().get(0);
		Assert.assertNotNull(st);
		
	}
}
