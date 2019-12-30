package com.bobo.frista.test.zookeeper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bobo.fristsba.FristsbaApplication;
import com.bobo.fristsba.config.ZkAPI;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@SpringBootTest(classes = FristsbaApplication.class)
public class ZooKeeperTest {

	@Autowired
	private ZkAPI zkAPI;
	
	@Test
	public void TestSet(){
		String path = "/zk_test";
		if(zkAPI.exists(path, false) == null)
			zkAPI.createNode(path, "student");
		else
			zkAPI.updateNode(path, "student");
		path = "/zk_test/1";
		if(zkAPI.exists(path, false) == null)
			zkAPI.createNode(path, "bobo.huang");
		else
			zkAPI.updateNode(path, "bobo.huang");
		path = "/zk_test/2";
		if(zkAPI.exists(path, false) == null)
			zkAPI.createNode(path, "happy.huang");
		else
			zkAPI.updateNode(path, "happy.huang");
		path = "/zk_test/3";
		if(zkAPI.exists(path, false) == null)
			zkAPI.createNode(path, "sunny.li");
		else
			zkAPI.updateNode(path, "sunny.li");
		path = "/zk_test/1";
		String val = zkAPI.getData(path, null);
		Assert.assertEquals("bobo.huang", val);
	}
}
