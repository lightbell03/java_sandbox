package com.example.sandbox.luajsontest;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LuaScript {

	public static String script(LocalDateTime accessDateTime) {
		ScriptBuilder script = new ScriptBuilder();

		script.append("local user = redis.call('GET', KEYS[1])");
		script.append("user = cjson.decode(user)");
		script.append("user.accessDateTime = '%s'", accessDateTime.toString());
		script.encodeWithEmptyArrayJson("user");
		script.append("redis.call('SET', KEYS[1], user)");
		script.append("return user");

		return script.toString();
	}

	private static class ScriptBuilder {
		private final StringBuilder sb = new StringBuilder();

		public ScriptBuilder append(String script, Object... args) {
			sb.append(String.format(script, args)).append("\n");
			return this;
		}

		public void encodeWithEmptyArrayJson(String jsonObj) {
			append("local function is_object(json)");
			append("    return type(json) == 'table'");
			append("end");

			append("local function is_empty_array(json)");
			append("    return type(json) == 'table' and next(json) == nil");
			append("end");

			append("local function encode_json(json)");
			append("    local result = {}");
			append("    for k, v in pairs(json) do");
			append("        if is_empty_array(v) then");
			append("            result[k] = { '__empty' }");
			append("        elseif is_object(v) then");
			append("            result[k] = encode_json(v)");
			append("        else");
			append("            result[k] = v");
			append("        end");
			append("    end");
			append("    return result");
			append("end");

			append("%s = encode_json(%s)", jsonObj, jsonObj);

			append("%s = cjson.encode(%s)", jsonObj, jsonObj);
			append("%s = %s:gsub('%%[\"__empty\"]', '[]')", jsonObj, jsonObj);
		}

		public String toString() {
			return sb.toString();
		}
	}
}
