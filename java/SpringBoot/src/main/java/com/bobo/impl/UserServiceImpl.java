package com.bobo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bobo.bean.User;
import com.bobo.mapper.UserMapper;
import com.bobo.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User getUserById(Integer id){
		return userMapper.getUserById(id);
	}

	@Override
	public List<User> getAllUser()
	{
		return userMapper.getUserList();
	}
	
	@Override
	public int add(User user){
		return userMapper.add(user);
	}
	
//	@Override
//	public int update(User user){
//		return this.userMapper.update(user);
//	}
	
	@Override
	public int update(Integer id, User user){
		return this.userMapper.update(id, user);
	}
	
	@Override
	public int delete(Integer id){
		return this.userMapper.delete(id);
	}
}
