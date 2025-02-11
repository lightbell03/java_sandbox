package com.example.sandbox.luajsontest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private String userId;
	private String password;
	private List<StringWrapper> roles = new ArrayList<>();
	private LocalDateTime accessDateTime;
}
