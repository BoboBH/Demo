package com.bobo.dao;

import java.util.List;

import com.bobo.bean.User;

public interface UserDao {

	User getUserById(int id);
	
	List<User> getUserList();
	
	public int add(User user);
	
	public int delete(int id);
	
	public int update(int id, User user);
}
