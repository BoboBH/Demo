package com.bobo.zktest.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.zktest.bean.Client;
import com.bobo.zktest.bean.ResponseClient;


@RestController
@RequestMapping("/clients")
public class ClientController {
	
	@RequestMapping("{id}")
	public ResponseClient getClient(@PathVariable String id){
		Client client = new Client();
		client.setId(id);
		client.setChinesename("黄齐仁");
		client.setAppid("100000");
		client.setFirstname("Bobo");
		client.setLastname("Huang");
		return new ResponseClient(0,null,client);
	}
	
	@RequestMapping(value="",method=RequestMethod.POST)
	public ResponseClient createClient(@RequestBody Client client){
		System.out.println(client);
		return new ResponseClient(0,null,client);
	}

}
