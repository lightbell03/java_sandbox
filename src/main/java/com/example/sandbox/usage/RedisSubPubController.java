package com.example.sandbox.usage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisSubPubController {
    private final Publisher publisher;

    public RedisSubPubController(Publisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping("/redis/pub/{topic}")
    public void test(@PathVariable("topic") String topic, @RequestParam("message") String message) {
        publisher.publishMessage(topic, message);
    }
}
