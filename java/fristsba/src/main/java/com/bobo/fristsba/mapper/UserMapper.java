package com.bobo.fristsba.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bobo.fristsba.domain.User;

public interface UserMapper {
	
	@Select("SELECT id,user_name as username,password FROM user")
	@Results({
	    })
	List<User> getAll();
	
	@Select("SELECT id,user_name as username,password FROM user where id=#{id}")
	@Results({
	    })
	User getUserById(String id);

	@Select("SELECT id,user_name as username,password FROM user where user_name=#{username} and password=#{password}")
	@Results({
	    })
	User getUserByNameAndPassword(User user);
	

	@Select("SELECT id,user_name as username,password FROM user where user_name=#{username}")
	@Results({
	    })
	List<User> getUserByName(String username);
	
	@Insert("insert into user(id,user_name,password) values(#{id},#{username},#{password})")
	void insert(User user);

	@Update("update user set user_name = #{username}, password=#{password} where id=#{id}")
	void update(User user);
	

	@Delete("delete from user where id=#{id}")
	void delete(String id);

}
