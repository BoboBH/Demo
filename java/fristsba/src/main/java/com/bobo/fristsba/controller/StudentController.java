package com.bobo.fristsba.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StudentController {

   @RequestMapping("/")
    public String index() {
        return "redirect:/index";
    }
   
   @RequestMapping("/index")
   public String index(Model model) {
       return "index";
   }
}
