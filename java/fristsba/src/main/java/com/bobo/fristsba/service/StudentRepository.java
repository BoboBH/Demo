package com.bobo.fristsba.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bobo.fristsba.domain.Student;

/**
 * 验证JPA的查询方法；
 * @author bobo.huang
 *
 */
@Repository
public interface StudentRepository extends JpaRepository<Student,String>,JpaSpecificationExecutor<Student>
{
	
	ArrayList<Student> findByName(String name);
	@Query("from Student where name =?1")
	public ArrayList<Student> findByNameWithSql(String name);
	
	@Query("select id from Student where name=?1")
	List<String> findIdsByName(String name);
	

	@Query(value="select id,name from student where name=?1",nativeQuery=true)
	List<Object> findObjectByName(String name);
	
	@Query(value="select * from student where name=?1", countQuery="select count(1) from student where name=?1",nativeQuery=true)
	Page<Student> findPageStudent(String name, Pageable pageable);
}
