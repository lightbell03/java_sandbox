package com.example.sandbox.luajsontest;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LuaScriptExecutor {
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;

	public String executeScriptWithOutDeserialize(String script, List<String> keyList) {
		return redisTemplate.execute(RedisScript.of(script, String.class), keyList);
	}

	public <R> R execute(String script, Class<R> clz, List<String> keyList) {
		String userJson = redisTemplate.execute(RedisScript.of(script, String.class), keyList);
		try {
			return objectMapper.readValue(userJson, clz);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
