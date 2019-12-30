package com.bobo.fristsba.config;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class ZooKeeperConfig {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(ZooKeeperConfig.class);
	@Value("${zookeeper.address}")
	private String connectionString;
	
	@Value("${zookeeper.timeout}")
	private int timeout;
	@Bean(name="zkClient")
	public ZooKeeper getZKClient(){
		ZooKeeper zooKeeper = null;
		try{
			final CountDownLatch countDownLatch = new CountDownLatch(1);
			zooKeeper = new ZooKeeper(connectionString, timeout, new Watcher(){
				@Override
				public void process(WatchedEvent event){
					if(Event.KeeperState.SyncConnected== event.getState()){
						countDownLatch.countDown();
					}
				}
			});
		}
		catch(Exception ex){
			log.error("Initialize zookeeper connection error:{}", ex);
		}
		return zooKeeper;
	}
}
