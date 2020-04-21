package com.robindrew.common.json;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Gsons {

	public static String prettyPrint(Object object) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(object);
	}
	
	public static IJson toJson(JsonObject object) {
		return new JsonObjectWrapper(object);
	}

	public static IJson parseJson(String json) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		JsonObject object = element.getAsJsonObject();
		return toJson(object);
	}

}
