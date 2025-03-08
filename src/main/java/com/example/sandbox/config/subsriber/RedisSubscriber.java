package com.example.sandbox.config.subsriber;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RedisSubscriber implements MessageListener {
    private final Method method;

    public RedisSubscriber(Method method) {
        this.method = method;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();

        Class<?>[] parameterTypes = method.getParameterTypes();
        Object argument;
        // todo parameter 가 0개 인 경우
        if (parameterTypes.length == 1) {
            Class<?> type = parameterTypes[0];
            ByteArrayInputStream bais = new ByteArrayInputStream(body);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(bais);
                argument = type.cast(objectInputStream.readObject());
            } catch (IOException e) {
                // todo 예외 메시지
                throw new IllegalArgumentException(e);
            } catch (ClassNotFoundException e) {
                // todo 예외 메시지
                throw new IllegalArgumentException(e);
            }
        } else {
            // todo exception message
            throw new IllegalArgumentException();
        }

        try {
            method.invoke(argument);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
