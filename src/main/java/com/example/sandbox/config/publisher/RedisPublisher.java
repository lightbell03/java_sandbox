package com.example.sandbox.config.publisher;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisPublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> void publish(String topic, T message) {
        redisTemplate.convertAndSend(topic, message);
    }
}
