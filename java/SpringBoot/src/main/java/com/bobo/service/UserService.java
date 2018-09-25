package com.bobo.service;
import com.bobo.bean.User;
import java.util.List;
public interface UserService {

	User getUserById(Integer id);
	public List<User> getAllUser();
	public int add(User user);
//	public int update(User user);
	public int update(Integer id, User user);
	public int delete(Integer id);
}
