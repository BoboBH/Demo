package com.bobo.fristsba.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZkWatchAPI implements Watcher {

	private ZkAPI zkAPI;
	public ZkWatchAPI(ZkAPI zkAPI)
	{
		this.zkAPI = zkAPI;
	}
	 private static final Logger logger = LoggerFactory.getLogger(ZkWatchAPI.class);
	    @Override
	    public void process(WatchedEvent event) {
	        logger.info("【Watcher监听事件】={}",event.getState());
	        String path = event.getPath();
	        logger.info("【监听路径为】={}",event.getPath());
	        logger.info("【监听的类型为】={}",event.getType()); //  三种监听类型： 创建，删除，更新
	        if(EventType.NodeDeleted != event.getType()){
	        	zkAPI.getData(path, new ZkWatchAPI(this.zkAPI));
	        }
	        
	    }

}
