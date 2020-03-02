package com.bobo.shirosample.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bobo.shirosample.bean.User;
import com.bobo.shirosample.service.UserService;

@Service
public class UserServiceImpl implements UserService {


	@Override 
	public User getUser(String username){
		if(StringUtils.isEmpty(username))
			return null;
		User user = new User();
		user.setUsername(username);
		user.setPassword("123456");
		return user;
	}
	@Override
	public List<String> getRoles(String username) {
		List<String> list = new ArrayList<String>();
		if("bobo.huang".equals(username))
			 list.add("admin");
		else
			list.add("guest");
		return list;
	}

}
