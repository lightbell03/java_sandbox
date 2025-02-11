package com.example.sandbox.luajsontest;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LuaScriptExecutor {
	private final ObjectMapper objectMapper;
}
