package com.bobo.demo.java;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
@SpringBootApplication
@Controller
public class HelloControl {
	@RequestMapping("hello/{name}")
	public String hello(@PathVariable("name") String name, Model model)
	{
		model.addAttribute("name",name);
		return "hello";
	}

}
