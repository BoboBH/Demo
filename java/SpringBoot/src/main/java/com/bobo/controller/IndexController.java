package com.bobo.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class IndexController {

	@GetMapping("/index2")
	public ResponseEntity helloWorld(){
		return ResponseEntity.ok("Hello world in index 2");
	}
}
