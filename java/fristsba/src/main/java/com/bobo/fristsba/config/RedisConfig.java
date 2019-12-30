package com.bobo.fristsba.config;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

	@Bean
	public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
		return new RedisLockRegistry(redisConnectionFactory, "spring-cloud");
	}

	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};

	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		// 初始化一个RedisCacheWriter
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
		// 设置CacheManager的值序列化方式为json序列化
		RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();
		RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
				.fromSerializer(jsonSerializer);
		RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(pair);
		// 设置默认超过期时间是30秒
		defaultCacheConfig.entryTtl(Duration.ofSeconds(30));		
		// 初始化RedisCacheManager
		//return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
		return new RedisCacheManager(redisCacheWriter, defaultCacheConfig,getRedisConfiguration());
	}
	
	private Map<String, RedisCacheConfiguration> getRedisConfiguration(){
		Map<String, RedisCacheConfiguration> map = new HashMap<>();
		map.put("stockcache", getRedisCacheConfigurationWihtTtl(600));
		return map;
	}
	
	private RedisCacheConfiguration getRedisCacheConfigurationWihtTtl(Integer seconds){
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
            RedisSerializationContext
                .SerializationPair
                .fromSerializer(jackson2JsonRedisSerializer)
        ).entryTtl(Duration.ofSeconds(seconds));

        return redisCacheConfiguration;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
		StringRedisTemplate template = new StringRedisTemplate(factory);
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();		
		return template;

	}
	
//	@Bean
//	public RedisTemplate<String, Object> redisObjectTemplate(RedisConnectionFactory factory) {
//		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//		@SuppressWarnings({ "rawtypes", "unchecked" })
//		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//		ObjectMapper om = new ObjectMapper();
//		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//		jackson2JsonRedisSerializer.setObjectMapper(om);
//		template.setValueSerializer(jackson2JsonRedisSerializer);
//		template.afterPropertiesSet();	
//		template.setConnectionFactory(factory);
//		return template;
//
//	}
}
