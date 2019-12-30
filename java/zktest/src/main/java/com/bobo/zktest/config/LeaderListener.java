package com.bobo.zktest.config;

import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeaderListener extends LeaderSelectorListenerAdapter{
	 private final Logger log = LoggerFactory.getLogger(LeaderListener.class);
	 private CountDownLatch countDownLatch = new CountDownLatch(1);
	 public  void takeLeadership(CuratorFramework client) throws Exception{
		 log.info("Continue Blocking... and will will provide service");
		 countDownLatch.await();
	 }
}
