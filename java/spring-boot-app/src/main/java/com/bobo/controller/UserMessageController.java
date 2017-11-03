/*package com.bobo.mybatis.bean;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.mybatis.bean.UserMessage;
import com.bobo.mybatis.bean.UserMessageService;
@RestController
public class UserMessageController {
	@Resource
	private UserMessageService userMessageService;
	
	@RequestMapping("userMessage/{id}")
	public List<UserMessage> getUserMessageById(int id){
		return  this.userMessageService.getUserMessageById(id);
	}

}
*/