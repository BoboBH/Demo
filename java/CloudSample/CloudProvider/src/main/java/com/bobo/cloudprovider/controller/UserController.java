package com.bobo.cloudprovider.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.cloudmodule.bean.ResponseUser;
import com.bobo.cloudmodule.bean.User;
import com.bobo.cloudmodule.client.UserFeignClient;

@RequestMapping("/user")
@RestController
public class UserController implements UserFeignClient{

	private static Map<Integer, User> map = new ConcurrentHashMap<Integer,User>();
	
	static{
		map.put(1, new User(1,"黄齐仁"));
		map.put(2, new User(2,"黄晓乐"));
		map.put(3, new User(3,"李云"));
		map.put(4, new User(4,"Anna Huang"));
	}

	@RequestMapping(value="get", method=RequestMethod.GET, produces={"application/json;charset=UTF-8"})
	@Override
	public ResponseUser findById(@RequestParam("id") Integer id) {
		if(map.containsKey(id)){
			return new ResponseUser(map.get(id));
		}
		return new ResponseUser(-1,"User does not exist", map.get(id));
	}

	@RequestMapping(value="add", method=RequestMethod.POST, produces={"application/json;charset=UTF-8"})
	@Override
	public ResponseUser addUser(@RequestBody User user) {
		map.put(user.getId(), user);
		return new ResponseUser(user);
	}
	
	
}
