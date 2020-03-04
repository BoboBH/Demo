package com.bobo.aopsample.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.aopsample.Controller.aop.AopLog;

@RestController
@RequestMapping("/start")
public class StartController {
	
	@RequestMapping("/harden")
	public String harden(){
		return "Harden is playing!!!";
	}
	
	@RequestMapping("/durant/{point}")
	public String durant(@PathVariable("point") int point){
		return "Harden is playing and got " + point + " points !!!";
	}
	
	@AopLog("jodic function")
	@RequestMapping("/jodic")
	public String jodic(){
		return "Jodic is playing!!!";
	}

}
