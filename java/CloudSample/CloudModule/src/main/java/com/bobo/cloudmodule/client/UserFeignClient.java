package com.bobo.cloudmodule.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bobo.cloudmodule.bean.ResponseUser;
import com.bobo.cloudmodule.bean.User;

@FeignClient(name="cloudprovider", fallbackFactory=UserFallback.class)
public interface UserFeignClient {

	@RequestMapping(value="/user/get", method=RequestMethod.GET)
	ResponseUser findById(@RequestParam("id") Integer id);
	
	@RequestMapping(value="/user/add", method=RequestMethod.POST)
	ResponseUser addUser(@RequestBody User user);
}
