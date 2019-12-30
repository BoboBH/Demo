package com.bobo.zktest.config;

import org.apache.commons.logging.Log;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * 
 * @author bobo.huang
 * Description:
 * 1.Test Note and Children Listener;
 * 2.Test Leader Selector
 */
@Configuration
public class ZooKeeperConfig {
	
	private final Logger log = LoggerFactory.getLogger(ZooKeeperConfig.class);
	private final String ChildrenParentPathListened = "/zkparent";
	private final String ChildPathListened = "/zkchild";
	@Value("${zookeeper.connectionstring}")
	private String zookeeperConnectionString;
	@Value("${zookeeper.datapath}")
	private String datapath;
	@Value("${zookeeper.timeout}")
	private Integer timeout;
	
	@Bean
	public CuratorFramework getZKClient(){
		CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, new ExponentialBackoffRetry(1000, 3));
		client.start();
		this.setPathCacheListener(client, ChildrenParentPathListened, true);
		this.setNodeCacheListener(client, ChildPathListened, false);
		return client;
	}
	@Bean
	public LeaderSelector getLeaderSelector(CuratorFramework client){
		LeaderSelector leaderSelector = new LeaderSelector(client, datapath, new LeaderListener());
		leaderSelector.start();
		return leaderSelector;
	}
	
	public void setPathCacheListener(CuratorFramework client, String path,boolean cacheData){
		try{
		PathChildrenCache pathChildrenCache = new PathChildrenCache(client,path,cacheData);
		PathChildrenCacheListener listener = new PathChildrenCacheListener() {
			
			@Override
			public void childEvent(CuratorFramework zkClient, PathChildrenCacheEvent event) throws Exception {
				// TODO Auto-generated method stub
				ChildData data = event.getData();
				switch(event.getType()){
					case CHILD_ADDED:
						log.info("Add Child Path={}, data={}", data.getPath(), new String(data.getData()));
						break;
					case CHILD_UPDATED:
						log.info("Update Child Path={}, data={}", data.getPath(), new String(data.getData()));
						break;
					case CHILD_REMOVED:
						log.info("Remove Child Path={}, data={}", data.getPath(), new String(data.getData()));
						break;
					default:
						break;
				}						
			}
		};
		pathChildrenCache.getListenable().addListener(listener);
		pathChildrenCache.start(StartMode.POST_INITIALIZED_EVENT);	
		}
		catch(Exception ex){
            log.error("PathCache监听失败, path=", path);
		}
	}
	
	public void setNodeCacheListener(CuratorFramework zkClient, String path, boolean dataIsCompressed){
		try{
			NodeCache nodeCache = new NodeCache(zkClient, path, dataIsCompressed);
			NodeCacheListener listener = new NodeCacheListener() {
				
				@Override
				public void nodeChanged() throws Exception {
					// TODO Auto-generated method stub
					ChildData childData = nodeCache.getCurrentData();
					if(childData == null)
						log.info("ZNode({}) is removed","");
					else
					{
						log.info("ZNode Status changed, path={}", childData.getPath());
						log.info("ZNode Status changed, data={}", new String(childData.getData()));
						log.info("ZNode Status changed, stat={}", childData.getStat());
					}
				}
			};
			nodeCache.getListenable().addListener(listener);
			nodeCache.start();
		}
		catch(Exception ex){
			log.error("Create NodeCache listener failed, path={}",path);
		}
	}

}
