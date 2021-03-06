package com.bobo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.bean.JsonResult;
import com.bobo.bean.User;
import com.bobo.dao.UserDao;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@ComponentScan("com.bobo.dao")
@RestController
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private UserDao userDao;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value="获取用户详细信息", notes = "根据URL中的Id来获取用户的详细信息")
	@ApiImplicitParam(value="用户id", name="id", required = true, dataType="int", paramType="path")
	@RequestMapping(value="/testuser/{id}", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getUserById(@PathVariable(value="id") int id){
		
		try{
			logger.info("try to get user({}) from redis", new Object[]{ id});
			String key = String.format("%d", id);
			Object obj = redisTemplate.opsForValue().get(key);
			if(obj == null){
				obj = this.userDao.getUserById(id);
				if(obj != null)
					redisTemplate.opsForValue().set(key, obj);
			}
			logger.info("retrieved user({}) successfully",  new Object[]{ id});
			return ResponseEntity.ok(new JsonResult(obj));
			
		}
		catch(Exception ex){
			return ResponseEntity.ok(new JsonResult(-1, "System Error:" + ex.getMessage()));
		}
	}

	@RequestMapping(value="/testusers", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getUserList(){
		try{
			List<User> list = this.userDao.getUserList();
			return ResponseEntity.ok(new JsonResult(list));			
		}
		catch(Exception ex){
			return ResponseEntity.ok(new JsonResult(-1, "System Error:" + ex.getMessage()));
		}
	}

	@ApiOperation(value="根据Id来更新user详细信息", notes = "根据URL中的Id来更新用户的详细信息")
	@ApiImplicitParams({
		@ApiImplicitParam(value="用户id", name="id", required = true, dataType="int", paramType="path"),
		@ApiImplicitParam(value="用户对象User", name="user", required = true, dataType="User"),
		})
	@RequestMapping(value="/testuser/{id}", method = RequestMethod.POST)
	public ResponseEntity<JsonResult> update(@PathVariable(value="id") int id, @RequestBody User user){
		try{
			
			 int result = this.userDao.update(id, user);
			 if(result >= 0)			
				 return ResponseEntity.ok(new JsonResult(0, "Update user successfully"));
			 else
				 return ResponseEntity.ok(new JsonResult(0, "Can not update user"));
		}
		catch(Exception ex){
			return ResponseEntity.ok(new JsonResult(-1, "System Error:" + ex.getMessage()));
		}
	}
	
	@RequestMapping(value="/testuser", method = RequestMethod.POST)
	public ResponseEntity<JsonResult> add(@RequestBody User user){
		try{
			
			 int result = this.userDao.add(user);
			 if(result >= 0)			
				 return ResponseEntity.ok(new JsonResult(0, "Add user successfully"));
			 else
				 return ResponseEntity.ok(new JsonResult(0, "Can not add user"));
		}
		catch(Exception ex){
			return ResponseEntity.ok(new JsonResult(-1, "System Error:" + ex.getMessage()));
		}
	}
	

	
	@RequestMapping(value="/testuser/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<JsonResult> delete(@PathVariable(value="id") int id){
		try{
			
			 int result = this.userDao.delete(id);
			 if(result >= 0)			
				 return ResponseEntity.ok(new JsonResult(0, "Delete user successfully"));
			 else
				 return ResponseEntity.ok(new JsonResult(0, "Can not delete user"));
		}
		catch(Exception ex){
			return ResponseEntity.ok(new JsonResult(-1, "System Error:" + ex.getMessage()));
		}
	}
}
