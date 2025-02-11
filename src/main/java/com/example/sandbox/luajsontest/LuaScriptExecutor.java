package com.example.sandbox.luajsontest;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LuaScriptExecutor {
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;

	public void executeScript(String script, List<String> keyList) {
		redisTemplate.execute(RedisScript.of(script, String.class), keyList);
	}
}
