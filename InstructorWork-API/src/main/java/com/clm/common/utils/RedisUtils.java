package com.clm.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @Author su
 * @Date 2021/11/25 10:35
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间（秒）
     * @return
     */
    public boolean setExpire(String key,long time){
        try {
            if (time>0){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return
     */
   public boolean judgeKey(String key){
        try {
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
   }

    /**
     * 删除缓存
     * @param keys 可以传一个值或多个值
     */
   @SuppressWarnings("unchecked")
   public void deleteKeys(String... keys){
       if (keys!=null && keys.length>0){
           if (keys.length==1){
               redisTemplate.delete(keys[0]);
           }else {
               redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(keys));
           }
       }
   }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return
     */
   public boolean setKey(String key,Object value){
       try {
           redisTemplate.opsForValue().set(key,value);
           return true;
       }catch (Exception e){
           e.printStackTrace();
           return false;
       }
   }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return
     */
    public boolean setKey(String key,Object value,long time){
        try {
            if (time>0){
                redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            }else {
                setKey(key,value);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
