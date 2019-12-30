package com.bobo.fristsba.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.fristsba.config.ZkAPI;
import com.bobo.fristsba.config.ZkWatchAPI;

import io.netty.util.internal.StringUtil;
@RestController
@RequestMapping("/zookeeper")
public class ZooKeeperController {

	@Autowired
	private ZkAPI zkAPI;

    @RequestMapping(value="{key}", method=RequestMethod.GET)
	public String getSimpleValue(@PathVariable String key){
		String path = key;
		if(StringUtil.isNullOrEmpty(path))
			return "";
		if(!path.startsWith("/"))
			path = "/" + path;
		return zkAPI.getData(path,null);
	}
    
    @RequestMapping(value="{key}", method=RequestMethod.POST)
   	public String getSimpleValue(@PathVariable String key, String data){
   		String path = key;
   		if(StringUtil.isNullOrEmpty(path))
   			return "";
   		if(!path.startsWith("/"))
   			path = "/" + path;
   		if(zkAPI.exists(path, false) == null)
   		{
   			zkAPI.createNode(path, data);
   		}
   		else
   			zkAPI.updateNode(path, data);
   		return zkAPI.getData(path,  new ZkWatchAPI(zkAPI));
   	}
	
}
