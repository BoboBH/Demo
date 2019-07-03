package com.bobo.fristsba.mapper;
import com.bobo.fristsba.domain.Student;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface StudentMapper {

	@Select("SELECT * FROM Student")
	@Results({
	    })
	List<Student> getAll();
	
	@Select("SELECT * FROM Student where id=#{id}")
	@Results({
	    })
	Student getStudentById(String id);
	
	@Insert("insert into student(id,name) values(#{id},#{name})")
	void insert(Student student);

	@Update("update student set name = #{name} where id=#{id}")
	void update(Student student);
	

	@Delete("delete from student where id=#{id}")
	void delete(String id);
}
