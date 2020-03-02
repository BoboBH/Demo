package com.bobo.shirosample.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/guest")
public class GuestController {

	@RequestMapping("message")
	public String message(){
		return "This is an guest message";
	}
}
