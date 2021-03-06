package com.bobo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.bean.JsonResult;
import com.bobo.bean.RedisUser;
import com.bobo.bean.User;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@ComponentScan("com.bobo.bean")
public class RedisUserController {

	private static final Logger logger = LoggerFactory.getLogger(RedisUserController.class);
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@ApiOperation(value="获取用户详细信息", notes = "根据URL中的Id来获取用户的详细信息")
	@ApiImplicitParam(value="用户id", name="id", required = true, dataType="int", paramType="path")
	@RequestMapping(value="/redisuser/{id}", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getUserById(@PathVariable(value="id") int id){
		
		try{
			logger.info("try to get user({}) from redis", new Object[]{ id});
			String key = String.format("%d", id);
			Object obj = redisTemplate.opsForValue().get(key);
			if(obj == null){
				obj = new RedisUser(id,"bobo Huang");
				redisTemplate.opsForValue().set(key, obj);
				logger.info("set redis user to redis successfully");
			}
			logger.info("retrieved user({}) successfully",  new Object[]{ id});
			return ResponseEntity.ok(new JsonResult(obj));
			
		}
		catch(Exception ex){
			return ResponseEntity.ok(new JsonResult(-1, "System Error:" + ex.getMessage()));
		}
	}
}
