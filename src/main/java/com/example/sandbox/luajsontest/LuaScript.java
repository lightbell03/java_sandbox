package com.example.sandbox.luajsontest;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LuaScript {

	public static String script(LocalDateTime accessDateTime) {
		ScriptBuilder script = new ScriptBuilder();

		script.append("local user = redis.call('GET', KEYS[0])");
		script.append("user = cjson.encode(user)");
		script.append("user['accessDateTime'] = %s", accessDateTime.toString());
		script.append("user = cjson.decode(user)");
		script.append("return redis.call('SET', KEYS[0], user)");

		return script.toString();
	}

	private static class ScriptBuilder {
		private final StringBuilder sb = new StringBuilder();

		public ScriptBuilder append(String script, Object... args) {
			sb.append(String.format(script, args)).append("\n");
			return this;
		}

		public String toString() {
			return sb.toString();
		}
	}
}
