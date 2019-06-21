package com.bobo.firstspringmvc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.bobo.firstspringmvc.bean.Student;

@Mapper
public interface StudentMapper {

	@Select("select id,name,age from student")
	@Results(
			{
				@Result(id=true, column="id",property="id"),
				@Result(column="name",property="name"),
				@Result(column="age",property="age"),
			}
			)
	List<Student> getAllStudent();
	@Select("select id,name,age from student where id=${id}")
	Student getStudentById(String id);
}
