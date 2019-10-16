package com.bobo.firstspringmvc.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.bobo.firstspringmvc.bean.School;
import com.bobo.firstspringmvc.bean.Student;
import com.bobo.firstspringmvc.bean.User;
@EnableWebMvc
@Controller
public class HelloController {

	private static String MESSAGE = "Welcome to Sprign MVC";
	
	@RequestMapping("hello")
	public ModelAndView showMessage(@RequestParam(value="name", required=false,defaultValue="Spring") String name){
		ModelAndView mv = new ModelAndView("hellospring");
		mv.addObject("message",MESSAGE);
		mv.addObject("name",name);
		return mv;
	}
	
	@RequestMapping(value="login",method={RequestMethod.GET})
	public ModelAndView login(){
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}
	@RequestMapping(value="login",method={RequestMethod.POST})	
	public ModelAndView login(User user){
		UsernamePasswordToken toekn = new UsernamePasswordToken(user.getName(), user.getPassword());
		toekn.setRememberMe(user.getRememberme());
		try{
			SecurityUtils.getSubject().login(toekn);
		}
		catch(Exception ex){
			return new ModelAndView("redirect:login"); 
		}

		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	@RequestMapping(value="logout")
	public ModelAndView logout(){
		try{
			SecurityUtils.getSubject().logout();
		}
		catch(Exception ex){
			return new ModelAndView("redirect:/"); 
		}

		ModelAndView mv = new ModelAndView("redirect:/");
		return mv;
	}
	
	
	@RequestMapping("authorize")
	public ModelAndView authorize(@RequestParam(value="loginname", required=false,defaultValue="Spring") String loginname,
			@RequestParam(value="password", required=false,defaultValue="Spring") String password){
		ModelAndView mv = new ModelAndView("login");
		if("bobo.huang".equals(loginname) && "123456".equals(password))
			mv = new ModelAndView("redirect:student");
		return mv;
	}
	
	@RequestMapping(value="message", method = RequestMethod.GET)
	public @ResponseBody String getMessage(){
		return "Hello World";
	}
	@RequestMapping(value="student/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Student getStudent(@PathVariable String id){
		return new Student(id, "Bobo Huang", 18);
	}
	

	@RequestMapping(value="school/{id}", method = RequestMethod.GET)
	@ResponseBody
	public School getSchool(@PathVariable String id){
		School school = new School();
		school.setId(id);
		school.setName("3rd Middle School Of Hanchan");
		return school;
	}
}
