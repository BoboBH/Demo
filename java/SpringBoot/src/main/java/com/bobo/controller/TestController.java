package com.bobo.controller;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("/index3")
	public ResponseEntity<String> helloWorld(){
		return ResponseEntity.ok("Test Hello world in index 3, suan le");
	}
}
