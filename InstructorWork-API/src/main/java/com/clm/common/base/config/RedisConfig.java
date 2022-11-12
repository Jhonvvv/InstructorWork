package com.clm.common.base.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


/**
 * @Author su
 * @Date 2021/11/22 20:52
 */
@EnableCaching
@Configuration
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {


    /**
     * RedisTemplate配置
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        log.info(" --- redis config init --- ");
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        RedisSerializer<String> redisSerializer=new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer=jacksonSerializer();
        redisTemplate.setConnectionFactory(factory);

        // key序列化
        redisTemplate.setKeySerializer(redisSerializer);
        // value序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hash key序列化
        redisTemplate.setHashKeySerializer(redisSerializer);
        // hash value序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory){
        RedisSerializer<String> redisSerializer=new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer=jacksonSerializer();
        RedisCacheConfiguration cacheConfiguration=RedisCacheConfiguration.defaultCacheConfig()
                // 配置序列化（解决乱码的问题）,过期时间6小时
                .entryTtl(Duration.ofHours(6))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jacksonSerializer()))
                .disableCachingNullValues();
        RedisCacheManager cacheManager=RedisCacheManager.builder(factory)
                .cacheDefaults(cacheConfiguration)
                .build();
        return cacheManager;

    }
    private Jackson2JsonRedisSerializer<Object> jacksonSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer<>(Object.class);
        //解决查询缓存转换异常的问题
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL,JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }
}
