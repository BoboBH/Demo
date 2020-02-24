package org.rabbitmqtest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@RequestMapping("/")
	public String Hello(){
		return "Welcome to visit Rabbitmq Test !";
	}
}
