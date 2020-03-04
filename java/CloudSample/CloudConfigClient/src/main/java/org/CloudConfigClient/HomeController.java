package org.CloudConfigClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@Value(value="${student.name}")
	private String studentname;
	
	@RequestMapping("/")
	public String home(){
		return "Welcome Visit Config Client";
	}
	
	@RequestMapping("studentname")
	public String springConfig(){
		return studentname;
	}
	
	
}
