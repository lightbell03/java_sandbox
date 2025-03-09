package com.example.sandbox.usage;

import com.example.sandbox.pubsub.annotation.RedisListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SubscribeListener {

    @RedisListener(topic = "test-topic")
    public void listenTest(MessageDto messageDto) {
        log.info(messageDto.getMessage());
    }
}
