package com.example.sandbox.luajsontest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class EmptyListJsonDeserializer extends StdDeserializer<List<?>> {
	protected EmptyListJsonDeserializer() {
		super(List.class);
	}

	@Override
	public List<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		if (p.isExpectedStartObjectToken()) {
			p.skipChildren();
			return Collections.emptyList();
		}

		JsonNode jsonNodeRoot = ctxt.readTree(p);
		List<Object> result = new ArrayList<>();
		for (JsonNode jsonNode : jsonNodeRoot) {
			JavaType contextualType = ctxt.getContextualType();
			result.add(ctxt.readTreeAsValue(jsonNode, Object.class));
		}

		return result;
	}
}
