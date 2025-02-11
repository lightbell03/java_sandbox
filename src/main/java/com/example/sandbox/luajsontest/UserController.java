package com.example.sandbox.luajsontest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/users/{id}")
	public ResponseEntity<Object> getUserWithNoDeserialize(@PathVariable("id") String id, @RequestParam(value = "deserialize", defaultValue = "false") boolean deserialize) {

		return null;
	}

	@GetMapping("/users/{id}/deserialize")
	public ResponseEntity<UserResponseDto> getUserWithDeserialize(@PathVariable("id") String id) {

		return null;
	}
}
