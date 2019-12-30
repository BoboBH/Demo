package com.bobo.fristsba.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.bytebuddy.asm.Advice.This;

@Component
public class ScheduleTask2 {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisLockRegistry redisLockRegistry;
	private int count = 0;
	private static final SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//@Scheduled(cron="*/1 * * * * ?")
	public void reportCurrentTime() throws InterruptedException{
		if(count > 5){
			System.out.println(this.getClass().getName()  + " is done. do not run again.");
			return;
		}
		count++;
		String key = "a";		
		ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
		Thread.sleep(2000);
		boolean locked = operations.setIfAbsent(key, this.getClass().getSimpleName());
		if(locked){
			stringRedisTemplate.expire(key, 5, TimeUnit.SECONDS);
			System.out.println( this.getClass().getSimpleName() + " got the key(" + key + ")");
			//stringRedisTemplate.delete(key);
		}
		else{
			System.out.println(this.getClass().getSimpleName() + " can not lock the key(" + key + ")");
			return;
		}
	}
}
