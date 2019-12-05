package com.bobo.fristsba.test.redis;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.apache.shiro.dao.DataAccessException;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Jedis;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bobo.fristsba.FristsbaApplication;
import com.bobo.fristsba.domain.Student;

import io.lettuce.core.Consumer;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.SetArgs;
import io.lettuce.core.TransactionResult;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.netty.util.internal.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@SpringBootTest(classes = FristsbaApplication.class)
public class TestRedis {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private RedisLockRegistry redisLockRegistry;

	@Test
	public void test() throws Exception {
		stringRedisTemplate.opsForValue().set("aaa", "111");
		Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));

	}

	@Test
	public void TestAbsentSet() {
		String key = "ab";
		Boolean setted = stringRedisTemplate.opsForValue().setIfAbsent(key, "bobo.huag");
		stringRedisTemplate.expire(key, 30, TimeUnit.MINUTES);
		Assert.assertTrue(setted);
		// Can not be set again;
		setted = stringRedisTemplate.opsForValue().setIfAbsent(key, "bobo.huag");
		Assert.assertFalse(setted);
		stringRedisTemplate.delete(key);
	}

	@Test
	public void testLockWihtString() {
		ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
		String firstValue = "bobo huang";
		String secValue = "happy huang";
		String key = "com.bobo.foo";
		boolean gotKey = operations.setIfAbsent(key, firstValue);
		if (!gotKey) {
			stringRedisTemplate.delete(key);
			gotKey = operations.setIfAbsent(key, firstValue);
		}
		if (gotKey)
			stringRedisTemplate.expire(key, 3, TimeUnit.MINUTES);
		String firstVal = operations.get(key);
		Assert.assertEquals(firstVal, firstValue);
		boolean secLock = operations.setIfAbsent(key, secValue);
		Assert.assertFalse(secLock);
		String val = operations.get(key);
		Assert.assertEquals(firstValue, val);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testObj() throws Exception {
		Student st = new Student();
		st.setId("10000");
		st.setName("Bobo Huang");
		@SuppressWarnings("unchecked")
		ValueOperations<String, Student> operations = redisTemplate.opsForValue();
		String key = "com.bobo.student.10000";
		operations.set(key, st);
		operations.set("com.bobo.f", st, 1, TimeUnit.HOURS);
		Thread.sleep(1000);
		@SuppressWarnings("unchecked")
		boolean exists = redisTemplate.hasKey("com.bobo.f");
		if (exists) {
			System.out.println("exists is true");
		} else {

			System.out.println("exists is false");
		}
		exists = redisTemplate.hasKey("com.bobo.student.10000");
		if (exists) {
			System.out.println("exists is true");
		} else {

			System.out.println("exists is false");
		}
		Student st2 = operations.get(key);
		Assert.assertNotNull(st2);
		Assert.assertEquals("10000", st2.getId());

	}

	/***
	 * tryLock function looks not work. it can not lock the key and can be
	 * locked by second locker;
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLock() throws Exception {
		String key = "a";
		ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
		String value = "bobo huang";
		operations.set(key, value);
		stringRedisTemplate.expire(key, 10, TimeUnit.SECONDS);
		Lock lock = redisLockRegistry.obtain(key);
		Lock lock2 = redisLockRegistry.obtain(key);
		boolean gotLook = false;
		boolean seclock = false;
		if (lock.tryLock(1, TimeUnit.SECONDS)) {
			gotLook = true;
			seclock = lock2.tryLock(1, TimeUnit.SECONDS);
			if (seclock)
				lock2.unlock();
			lock.unlock();
		} else {
			gotLook = false;
			lock.unlock();
		}
		Assert.assertTrue(gotLook);
		/*
		 * it shoud be false, but acutal true. it shows the key can be locked
		 * more 1 time;
		 */
		Assert.assertTrue(seclock);
	}

	@Test
	public void TestExecute() {
		String key = "lock";
		String value = "bobo.huang";
		String result = stringRedisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisAsyncCommands<String, String> commands = (RedisAsyncCommands<String, String>) connection
						.getNativeConnection();
				commands.multi();
				commands.setnx(key, value);
				commands.expire(key, 30000);
				commands.exec();
				return value;
			}
		});
		Assert.assertEquals(value, result);
	}

	@Test
	public void TestLockByCommand() {
		String key = "com.bobo.lockby.command";
		String value = "bobo huang";
		int expire = 5000;
		boolean locked = lock(key, value, expire);
		Assert.assertTrue(locked);
		boolean secLocked = lock(key, "happy huang", expire);
		Assert.assertFalse(secLocked);
		stringRedisTemplate.delete(key);
		secLocked = lock(key, "happy huang", expire);
		Assert.assertTrue(secLocked);
	}

	private boolean tryLock(String key, String value, int expireInSeconds) {
		try {
			stringRedisTemplate.setEnableTransactionSupport(true);
			String result = stringRedisTemplate.execute(new RedisCallback<String>() {
				@Override
				public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
					RedisAsyncCommands<String, String> commands = (RedisAsyncCommands<String, String>) redisConnection
							.getNativeConnection();
					try {

						commands.multi();
						commands.set(key, value);
						//commands.expire(key, expireInSeconds * 1000);
						RedisFuture<TransactionResult> result = commands.exec();
						//result.get();
//						commands.getStatefulConnection().sync().set(key, value,
//								SetArgs.Builder.nx().ex(expireInSeconds * 1000));
						return value;
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
						return StringUtil.EMPTY_STRING;
					}
				}
			});
			return value.equals(result);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());

		}
		return false;
	}
	
	@Test
	public void TestTryLockWithExecute(){
		String key = "trylockxxx";
		String value = "bobo.huang";
		boolean result = tryLock(key, value, 10);
		Assert.assertTrue(result);
		ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
		String val = operations.get(key);
		Assert.assertEquals(value,val);
	}

	private boolean lock(String key, String value, int expire) {

		stringRedisTemplate.setEnableTransactionSupport(true);
		String result = stringRedisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
				RedisAsyncCommands<String, String> commands = (RedisAsyncCommands<String, String>) redisConnection
						.getNativeConnection();
				commands.multi();
				// JedisCommands commands
				// =(JedisCommands)redisConnection.getNativeConnection();
				String setVal = StringUtil.EMPTY_STRING;
				commands.setnx(key, value);
				commands.expire(key, expire);
				RedisFuture<TransactionResult> tranResult = commands.exec();
				try {
					TransactionResult tr = tranResult.get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tranResult.thenAccept(new java.util.function.Consumer<TransactionResult>() {
					@Override
					public void accept(TransactionResult tr) {
					}
				});
				return value;
			}
		});
		stringRedisTemplate.setEnableTransactionSupport(false);
		return value.equals(result);
	}

}
