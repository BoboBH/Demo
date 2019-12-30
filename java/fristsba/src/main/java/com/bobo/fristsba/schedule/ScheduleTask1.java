package com.bobo.fristsba.schedule;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.bytebuddy.asm.Advice.This;
@Component
public class ScheduleTask1 {


	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisLockRegistry redisLockRegistry;
	private int count = 0;
	//@Scheduled(cron="*/1 * * * * ?")
	private void process() throws InterruptedException{
		if(count > 5){
			System.out.println(this.getClass().getName()  + " is done. do not run again.");
			return;
		}
		count++;
		String key = "a";		
		ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
		boolean locked = operations.setIfAbsent(key, this.getClass().getSimpleName());
		if(locked){
			stringRedisTemplate.expire(key, 300, TimeUnit.SECONDS);
			System.out.println( this.getClass().getSimpleName() + " got the key(" + key + ")");
			//stringRedisTemplate.delete(key);
		}
		else{
			System.out.println(this.getClass().getSimpleName() + " can not lock the key(" + key + ")");
			return;
		}
	}
}
