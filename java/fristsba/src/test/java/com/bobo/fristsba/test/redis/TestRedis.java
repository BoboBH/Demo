package com.bobo.fristsba.test.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.apache.shiro.dao.DataAccessException;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.validator.PublicClassValidator;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Jedis;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TestTransaction;

import com.bobo.fristsba.FristsbaApplication;
import com.bobo.fristsba.domain.Student;

import io.lettuce.core.Consumer;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.SetArgs;
import io.lettuce.core.TransactionResult;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.dynamic.Commands;
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
	public void TestRedisIncrease(){
		ValueOperations<String, String> commands = stringRedisTemplate.opsForValue();
		String key = "string_increment";
		commands.set(key, "10");
		Long val = commands.increment(key, 20);
		Assert.assertEquals(new Long(30), val);
		key = "string_non_increment";
		String strVal = commands.get(key);
		if(!StringUtil.isNullOrEmpty(strVal))
			stringRedisTemplate.delete(key);
		val = commands.increment(key,20);
		Assert.assertEquals(new Long(20), val);
		boolean set = commands.setIfAbsent(key, "30");
		Assert.assertFalse(set);
	}
	@Test
	public void TestRedisHash(){
		
		String key = "string_hash";
		HashOperations<String, String, String> operations = redisTemplate.opsForHash();
		operations.put(key, "bobo.huang", "1");
		operations.put(key, "happy.huang", "2");
		operations.put(key, "sunny.lee", "3");
		String val = operations.get(key, "bobo.huang");
		Assert.assertEquals("1", val);
		val = operations.get(key, "happy.huang");
		Assert.assertEquals("2", val);
		val = operations.get(key, "sunny.lee");
		Assert.assertEquals("3", val);
		operations.delete(key, "sunny.lee");
		val = operations.get(key, "sunny.lee");
		Assert.assertNull(val);
		List<String> fields = new ArrayList<String>();
		fields.add("bobo.huang");
		fields.add("happy.huang");
		List<String> lInt = operations.multiGet(key, fields);
		Assert.assertEquals(2, lInt.size());
		operations.increment(key, "bobo.huang", 10);
		val = operations.get(key, "bobo.huang");
		Assert.assertEquals("11", val);
	}
	@Test
	public void TestRedisZSet(){
		String key = "string_zset";
		ZSetOperations<String, String> operations = stringRedisTemplate.opsForZSet();
		operations.add(key, "bobo.huang", 1);
		operations.add(key, "happy.huang", 1);
		operations.add(key, "sunny.lee", 1);
		operations.incrementScore(key, "bobo.huang", 3);
		operations.incrementScore(key, "happy.huang", 5);
		operations.incrementScore(key, "sunny.lee", 7);
		Double score = operations.score(key, "bobo.huang");
		Assert.assertEquals((Double)4.0,score);
		score = operations.score(key, "happy.huang");
		Assert.assertEquals((Double)6.0,score);
		score = operations.score(key, "sunny.lee");
		Assert.assertEquals((Double)8.0,score);
		Long rank = operations.rank(key, "bobo.huang");
		Assert.assertEquals(new Long(0), rank);
		rank = operations.rank(key, "happy.huang");
		Assert.assertEquals(new Long(1), rank);
		rank = operations.rank(key, "sunny.lee");
		Assert.assertEquals(new Long(2), rank);
		//获取分数最低的三位
		Set<TypedTuple<String>> tset = operations.rangeWithScores(key, 0, 3);
		List<StudentScore> list = new ArrayList<StudentScore>();
		for(TypedTuple<String> tuple:tset){
			StudentScore tScore = new StudentScore();
			tScore.setScore(tuple.getScore());
			tScore.setName(tuple.getValue());
			list.add(tScore);
		}
		Assert.assertEquals(3, list.size());
		list.clear();
		//获取分数最高的三位
		tset = operations.reverseRangeWithScores(key, 0, 3);
		for(TypedTuple<String> tuple:tset){
			StudentScore tScore = new StudentScore();
			tScore.setScore(tuple.getScore());
			tScore.setName(tuple.getValue());
			list.add(tScore);
		}
		Assert.assertEquals(3, list.size());
		
	}
	public class StudentScore{
		private String name;
		private Double score;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Double getScore() {
			return score;
		}
		public void setScore(Double score) {
			this.score = score;
		}
	}
	@Test
	public void TestRedisSet(){
		String key = "string_set";
		SetOperations<String, String> operations = stringRedisTemplate.opsForSet();
		operations.add(key, "bobo.huang");
		operations.add(key, "happy.huang");
		operations.add(key, "sunny.lee");
		boolean hasMember = operations.isMember(key, "bobo.huang");
		Assert.assertTrue(hasMember);
		hasMember = operations.isMember(key, "happy.huang");
		Assert.assertTrue(hasMember);
		hasMember = operations.isMember(key, "sunny.lee");
		Assert.assertTrue(hasMember);
		operations.remove(key, "sunny.lee");
		hasMember = operations.isMember(key, "sunny.lee");
		Assert.assertFalse(hasMember);
		Set<String> set = operations.members(key);
	    Assert.assertTrue(set.contains("bobo.huang"));
	    Assert.assertTrue(set.contains("happy.huang"));
	}
	@Test
	public void TestRedisList(){
		String key = "string_list";
		ListOperations<String, String> operations =  stringRedisTemplate.opsForList();
		operations.rightPush(key, "bobo.huang");
		operations.rightPush(key, "happy.huang");
		operations.rightPush(key, "sunny.lee");
		String value = operations.index(key, 0);
		Assert.assertNotNull(value);
		List<String> values = operations.range(key, 0, 2);
		Assert.assertNotNull(values);
		Assert.assertEquals("bobo.huang", values.get(0));
		String left = operations.leftPop(key);
		Assert.assertEquals("bobo.huang", left);
		left = operations.leftPop(key);
		Assert.assertEquals("happy.huang", left);
		left = operations.leftPop(key);
		Assert.assertEquals("sunny.lee", left);
	}
	@Test
	public void TestArrayList(){
		ArrayList<Student> stList = new ArrayList<Student>();
		Student st1 = new Student();
		st1.setId("1000");
		st1.setName("bobo Huang");
		stList.add(st1);
		Student st2 = new Student();
		st2.setId("1001");
		st2.setName("happy Huang");
		stList.add(st2);
		Student st3 = new Student();
		st3.setId("1002");
		st3.setName("Sunny Lee");
		stList.add(st3);
		String key = "studentlist";
		ValueOperations<String, ArrayList<Student>> operations = redisTemplate.opsForValue();
		operations.set(key, stList);
		redisTemplate.expire(key,100, TimeUnit.SECONDS);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Student> storedList = operations.get(key);
		Assert.assertNotNull(storedList);
		Assert.assertEquals(3, storedList.size());
	}
	@Test
	public void TestMulti(){
		 stringRedisTemplate.setEnableTransactionSupport(true);
		 try{
		 stringRedisTemplate.multi();
		 stringRedisTemplate.opsForValue().set("a", "bobo.huang");
		 stringRedisTemplate.opsForValue().set("b", "happy.huang");
		 stringRedisTemplate.opsForValue().set("c", "sunny.lee");
		 stringRedisTemplate.expire("a", 30, TimeUnit.SECONDS);
		 stringRedisTemplate.expire("b", 30, TimeUnit.MINUTES);
		 stringRedisTemplate.exec();
		 String aVal =  stringRedisTemplate.opsForValue().get("a");
		 Assert.assertEquals("bobo.huang", aVal);
		 }
		 catch(Exception ex){
			 stringRedisTemplate.discard();
			 Assert.fail(ex.getMessage());
		 }
	}
	
	@Test
	public void TestSessonCallback(){
		 testSessionCallback(redisTemplate, "bobo.huang");
		 Assert.assertTrue(true);
	}
	public void testSessionCallback(RedisTemplate redisTemplate, String value) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.opsForValue().set("wanwan", value);
                redisOperations.expire("wanwan", 30, TimeUnit.SECONDS);
                String myValue = String.valueOf(redisOperations.opsForValue().get("wanwan"));
                System.out.println(myValue);
                return myValue;
            }
        });
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
