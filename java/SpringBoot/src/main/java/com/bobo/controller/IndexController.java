package com.bobo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@GetMapping("/index2")
	public ResponseEntity helloWorld(){
		return ResponseEntity.ok("Hello world in index 2");
	}
}
