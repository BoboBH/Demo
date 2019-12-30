package com.bobo.zktest;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@SpringBootTest(classes = ZktestApplication.class)
public class ZooKeeperTest {

	private final Logger log = LoggerFactory.getLogger(ZooKeeperTest.class);
	@Autowired
	private CuratorFramework zkClient;
	
	@Test
	public void testTransaction(){
		String createKey = "/tran/create";
		String updateKey = "/tran/update";
		String delKey = "/tran/delete";
		try {
			if(zkClient.checkExists().forPath(createKey) != null)
				zkClient.delete().forPath(createKey);			
			if(zkClient.checkExists().forPath(updateKey) == null)
				zkClient.create().forPath(updateKey,"update".getBytes());
			if(zkClient.checkExists().forPath(delKey) == null)
				zkClient.create().forPath(delKey,"delete".getBytes());
			Collection<CuratorTransactionResult> results = zkClient.inTransaction()
					.create().forPath(createKey,"create node".getBytes())
					.and()
					.setData().forPath(updateKey,"update data".getBytes())
					.and()
					.delete().forPath(delKey)
					.and()
					.commit();
			for (CuratorTransactionResult curatorTransactionResult : results) {
				log.info(curatorTransactionResult.getForPath() + "-" + curatorTransactionResult.getType());
			}
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Can not execute transction due to error:{}", e.getMessage());
			log.error(e.getMessage(),e);
		}
	}
	
	@Test
	public void testInterProcessMutex(){
		boolean locked = false;
		InterProcessMutex lock = new InterProcessMutex(zkClient,"/lock");
		try{
			locked = lock.acquire(10, TimeUnit.SECONDS);
		}
		catch(Exception ex){			
			log.error(ex.getMessage(), ex);
			locked = false;
		}
		Assert.assertTrue(locked);
		try{
			log.info("Do something....");
			Thread.sleep(1000);
		}
		catch(Exception ex){
			
		}
		finally {
			try{
				lock.release();
			}
			catch(Exception e){
				log.error("Can not release the lock", e);
			}
		}
	}
}
