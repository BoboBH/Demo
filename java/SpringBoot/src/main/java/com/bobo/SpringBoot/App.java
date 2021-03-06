package com.bobo.SpringBoot;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.bean.JsonResult;
import com.bobo.bean.User;
import com.bobo.service.UserService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 * ComponentScan:支持多个controller,支持扫描多个包，例如("package1, package2, package3")
 */
@RestController
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 3600)
@EnableAutoConfiguration
@EnableSwagger2
@MapperScan("com.bobo.mapper")
@ComponentScan("com.bobo.impl,com.bobo.service,com.bobo.controller")
public class App extends SpringBootServletInitializer
{
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // TODO Auto-generated method stub
//      return super.configure(builder);
        return builder.sources(this.getClass());
    }
	
	@Autowired
	private UserService userService;
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
    
    @GetMapping("/index")
	public ResponseEntity helloWorld(HttpSession httpSession){
		return ResponseEntity.ok("Hello world");
	} 
    
    @GetMapping("/student/{name}")
   	public ResponseEntity student(HttpSession httpSession, @PathVariable String name){
    	Student st = new Student(name, new Date());
   		return ResponseEntity.ok(st);
   	}
    @GetMapping("/getstudent/{name}")
   	public Student getStudent(@PathVariable String name){
    	Student st = new Student(name, new Date());
    	return st;
   	}
    
    
    @GetMapping("/helloword")
    public ResponseEntity hello(HttpSession httpSession) {
        //return ResponseEntity.ok(httpSession.getAttribute("user"));
        return ResponseEntity.ok("default");
    }
    
    @RequestMapping(value="/user/{id}", method=RequestMethod.GET)
    public ResponseEntity<JsonResult> getUserById(@PathVariable Integer id){
    	
    	try{
    		User user = this.userService.getUserById(id);
    		JsonResult result = null;
    		if(user == null)
    			result = new JsonResult(-1, "User does not exist");
    		else
    			result = new JsonResult(user);
    		return ResponseEntity.ok(result);
    	}
    	catch(Exception ex){
    		return ResponseEntity.ok(new JsonResult(1000, "System Error: " + ex.getMessage()));
    	}
    }
    
    @RequestMapping(value="/users", method=RequestMethod.GET)
    public ResponseEntity<JsonResult> getUserList(){
    	
    	try{
    		List<User> list = this.userService.getAllUser();
    		JsonResult result =  new JsonResult(list);
    		return ResponseEntity.ok(result);
    	}
    	catch(Exception ex){
    		return ResponseEntity.ok(new JsonResult(1000, "System Error: " + ex.getMessage()));
    	}
    }
    
    
    @RequestMapping(value="/user", method=RequestMethod.POST)
    public ResponseEntity<JsonResult> add(@RequestBody User user){
    	
    	try{
    		int id = this.userService.add(user);
    		JsonResult result = null;
    		if(id < 0)
    			result = new JsonResult(-1, "fail");
    		else
    		{
    			User addedUser = this.userService.getUserById(user.getId());
    			result = new JsonResult(addedUser);
    		}
    		return ResponseEntity.ok(result);
    	}
    	catch(Exception ex){
    		return ResponseEntity.ok(new JsonResult(1000, "System Error: " + ex.getMessage()));
    	}
    }
    

    
    @RequestMapping(value="/user/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<JsonResult> add(@PathVariable(value="id") int id){
    	
    	try{
    		int rtnId = this.userService.delete(id);
    		JsonResult result = null;
    		if(rtnId < 0)
    			result = new JsonResult(-1, "fail");
    		else
    			result = new JsonResult(0,"delete user successfully");
    		return ResponseEntity.ok(result);
    	}
    	catch(Exception ex){
    		return ResponseEntity.ok(new JsonResult(1000, "System Error: " + ex.getMessage()));
    	}
    }
}
