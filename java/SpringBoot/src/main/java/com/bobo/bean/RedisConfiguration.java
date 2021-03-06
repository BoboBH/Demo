package com.bobo.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory factory){
		 RedisTemplate<String, Object> template = new  RedisTemplate<String, Object>();
		 template.setConnectionFactory(jedisConnectionFactory);
		 template.setKeySerializer(new StringRedisSerializer());
		 template.setValueSerializer(new JdkSerializationRedisSerializer());
		 template.afterPropertiesSet();
		 return template;
	}
}
