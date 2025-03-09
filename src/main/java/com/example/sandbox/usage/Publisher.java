package com.example.sandbox.usage;

import com.example.sandbox.pubsub.publisher.RedisPublisher;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
    private final RedisPublisher redisPublisher;

    public Publisher(RedisPublisher redisPublisher) {
        this.redisPublisher = redisPublisher;
    }

    public void publishMessage(String topic, String message) {
        redisPublisher.publish(topic, new MessageDto(message));
    }
}
