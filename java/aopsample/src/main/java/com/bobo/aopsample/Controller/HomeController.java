package com.bobo.aopsample.Controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	public String home(){
		return "Welcome Visit AOP Sample";
	}
}
