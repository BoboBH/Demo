package com.bobo.demo_thymeleaf.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
    public String index(Model model) {
        return "index";
    }
	
	@RequestMapping("/list")
	 public String list(Model model) {
        ArrayList<String> users= new ArrayList<String>();
        users.add("bobo Huang");
        users.add("happy Huang");
        model.addAttribute("users", users);
        return "list";
    }
}
