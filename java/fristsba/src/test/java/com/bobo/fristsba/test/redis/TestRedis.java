package com.bobo.fristsba.test.redis;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bobo.fristsba.FristsbaApplication;
import com.bobo.fristsba.domain.Student;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@SpringBootTest(classes=FristsbaApplication.class)
public class TestRedis {
	@Autowired
    private StringRedisTemplate stringRedisTemplate;  
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));

    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void testObj() throws Exception {
        Student st=new Student();
        st.setId("10000");
        st.setName("Bobo Huang");        
        @SuppressWarnings("unchecked")
		ValueOperations<String, Student> operations=redisTemplate.opsForValue();
        String key = "com.bobo.student.10000";
        operations.set(key, st);
        operations.set("com.bobo.f", st,1,TimeUnit.HOURS);
        Thread.sleep(1000);
        @SuppressWarnings("unchecked")
		boolean exists=redisTemplate.hasKey("com.bobo.f");
        if(exists){
            System.out.println("exists is true");
        }else{

            System.out.println("exists is false");
        }
        exists=redisTemplate.hasKey("com.bobo.student.10000");
        if(exists){
            System.out.println("exists is true");
        }else{

            System.out.println("exists is false");
        }
        Student st2 = operations.get(key);
        Assert.assertNotNull(st2);
        Assert.assertEquals("10000", st2.getId());

    }

}
