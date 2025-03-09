package com.example.sandbox.pubsub.config;

import com.example.sandbox.pubsub.annotation.RedisListener;
import com.example.sandbox.pubsub.publisher.RedisPublisher;
import com.example.sandbox.pubsub.subsriber.RedisSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.util.Set;

@Configuration
public class RedisConfig {
    private final ApplicationContext applicationContext;

    public RedisConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());

        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(objectMapper, Object.class));

        return redisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackage("")
                .addScanners(Scanners.MethodsAnnotated));

        Set<Method> redisListenerAnnotationMethodSet = reflections.getMethodsAnnotatedWith(RedisListener.class);
        for (Method method : redisListenerAnnotationMethodSet) {
            Class<?> declaringClass = method.getDeclaringClass();
            Object declareClassBean = applicationContext.getBean(declaringClass);
            RedisListener redisListener = method.getAnnotation(RedisListener.class);
            String topic = redisListener.topic();
            container.addMessageListener(new RedisSubscriber(declareClassBean, method, objectMapper), new ChannelTopic(topic));
        }

        return container;
    }

    @Bean
    public RedisPublisher redisPublisher(RedisTemplate<String, Object> redisTemplate) {
        return new RedisPublisher(redisTemplate);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
