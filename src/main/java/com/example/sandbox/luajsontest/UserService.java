package com.example.sandbox.luajsontest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final LuaScriptExecutor executor;
	private final UserRedisRepository userRedisRepository;

	public Object getUserWithoutDeserialize(String id) {
		return executor.executeScriptWithOutDeserialize(LuaScript.script(LocalDateTime.now()), List.of(id));
	}

	public User getUserWithDeserialize(String id) {
		return executor.execute(LuaScript.script(LocalDateTime.now()), User.class, List.of(id));
	}

	@PostConstruct
	public void init() {
		User adminUser = User.builder()
			.userId("admin")
			.password("password")
			.roles(List.of(new StringWrapper("admin")))
			.accessDateTime(LocalDateTime.now())
			.build();
		User normalUser = User.builder()
			.userId("user")
			.password("password")
			.roles(Collections.emptyList())
			.accessDateTime(LocalDateTime.now())
			.build();

		userRedisRepository.put(adminUser.getUserId(), adminUser);
		userRedisRepository.put(normalUser.getUserId(), normalUser);
	}
}
