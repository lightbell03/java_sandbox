package com.example.sandbox.luajsontest;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@RequiredArgsConstructor
public class UserVo {
	private final String userId;
	private final String password;
	private final List<String> roles;
	private final LocalDateTime accessDateTime;
}
