package com.example.sandbox.pubsub.subsriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RedisSubscriber implements MessageListener {
    private final Object declareClassBean;
    private final ObjectMapper objectMapper;
    private final Method method;

    public RedisSubscriber(Object declareClassBean, Method method, ObjectMapper objectMapper) {
        this.declareClassBean = declareClassBean;
        this.method = method;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();


        Class<?>[] parameterTypes = method.getParameterTypes();
        Object argument;
        Class<?> type = parameterTypes[0];
        // todo parameter 가 0개 인 경우
        if (parameterTypes.length == 1) {
            try {
                argument = convertBodyToParameter(body, type);
            } catch (IOException e) {
                // todo 예외 메시지
                throw new IllegalArgumentException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            // todo exception message
            throw new IllegalArgumentException();
        }
    }

    private <T> T convertBodyToParameter(byte[] body, Class<T> parameterType) throws IOException, InvocationTargetException, IllegalAccessException {
        T obj = objectMapper.readValue(body, parameterType);
        method.invoke(declareClassBean, obj);
        return obj;
    }
}
