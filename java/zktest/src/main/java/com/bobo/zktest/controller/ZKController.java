package com.bobo.zktest.controller;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/zktest")
public class ZKController {
	private final String RootPath="/zktest";
	@Autowired
	private CuratorFramework zkClient;
	

	
	@RequestMapping(value="{key}", method=RequestMethod.GET)
	public String getZKValue(@PathVariable String key){
		try{
			return new String(zkClient.getData().forPath(RootPath + "/" + key));
		}
		catch(Exception ex){
			return null;
		}
	}
	@RequestMapping(value="{key}", method=RequestMethod.POST)
	public String getZKValue(@PathVariable String key, String data){
		
		String path = RootPath + "/" + key;
		try{
			if(zkClient.checkExists().forPath(RootPath) == null)
				zkClient.create().withMode(CreateMode.PERSISTENT).forPath(RootPath,"root".getBytes());
			if(zkClient.checkExists().forPath(path) == null)
				zkClient.create().withMode(CreateMode.PERSISTENT).forPath(path, data.getBytes());
			else
				zkClient.setData().forPath(path, data.getBytes());
			return data;
		}
		catch(Exception ex){
			return null;
		}
	}

}
