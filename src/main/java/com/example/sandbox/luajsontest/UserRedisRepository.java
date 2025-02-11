package com.example.sandbox.luajsontest;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRedisRepository {
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;

	public void put(String key, UserVo userVo) {
		log.info("key = {}, value = {}", key, userVo);
		try {
			String valueJson = objectMapper.writeValueAsString(userVo);
			log.info("converted value json = {}", valueJson);
			redisTemplate.opsForValue().set(key, valueJson);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
