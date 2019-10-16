package com.bobo.firstspringmvc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.bobo.firstspringmvc.bean.User;
import com.bobo.firstspringmvc.bean.UserRole;

@Mapper
public interface UserMapper {

	@Select("select id,user_name as name,email,mobile,password from user where email=#{email}")
	@Results(
			{
				@Result(id=true, column="id",property="id"),
				@Result(column="name",property="name"),
				@Result(column="email",property="email"),
				@Result(column="mobile",property="mobile"),
				@Result(column="password",property="password"),
			}
			)
	List<User> getUserByEmail(String email);
	List<User> getAllUsers();
	List<User> searchUser(User sample);
	void addUser(User user);
	void updateUser(User user);
	void deleteUser(Integer id);
	User getUserById(Integer id);
	User getUserByUsername(String username);
	/*@Select("select id,user_name as name,email,mobile,password from user where id=#{id}")
	User getUserById(Integer id);*/
}
