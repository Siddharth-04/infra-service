package com.infra.service.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private RedisTemplate<String,String> redisTemplate;

    public RedisService(RedisTemplate<String,String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    private String prefix = "infra";

    private String buildKey(String module,String key){
        return prefix + ":" + module + ":" + key;
    }

    public void saveKey(String module,String key, String value, Long ttl){
        String finalKey = buildKey(module,key);

        if (ttl != null && ttl > 0){
            redisTemplate.opsForValue().set(finalKey,value, Duration.ofSeconds(ttl));
        }
        else{
            redisTemplate.opsForValue().set(finalKey,value);
        }
    }

    public String get(String module,String key){
        String finalKey = buildKey(module, key);
        String toReturn =  redisTemplate.opsForValue().get(finalKey);
        System.out.println(toReturn);
        return toReturn;
    }

    public Boolean exist(String module, String key){
        String finalKey = buildKey(module, key);
        return redisTemplate.hasKey(finalKey);
    }

    public Boolean delete(String module, String key){
        String finalKey = buildKey(module, key);
        return redisTemplate.delete(finalKey);
    }
}
