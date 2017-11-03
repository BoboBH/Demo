package com.bobo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bobo.mybatis.bean.UserMessage;
import com.bobo.mybatis.mapper.UserMessageMapper;

@Service
public class UserMessageService {

	@Autowired
	private UserMessageMapper userMessageMapper;
	public List<UserMessage> getUserMessageById(int id){
		return userMessageMapper.getUserMessageById(id);
	}
	
	public void save(UserMessage userMessage){
		this.userMessageMapper.save(userMessage);
	}
}
