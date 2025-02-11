package com.example.sandbox.luajsontest;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserVo {
	private final String userId;
	private final String password;
	private final List<String> roles;
	private final LocalDateTime accessDateTime;
}
