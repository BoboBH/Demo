package com.bobo.shirosample.service;

import java.util.List;

import com.bobo.shirosample.bean.User;

public interface UserService {
	
	List<String> getRoles(String username);
	
	User getUser(String username);

}
