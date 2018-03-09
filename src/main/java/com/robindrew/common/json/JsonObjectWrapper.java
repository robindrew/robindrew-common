package com.robindrew.common.json;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonObjectWrapper implements IJson {

	private final JsonObject object;

	public JsonObjectWrapper(JsonObject object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return object.toString();
	}

	private boolean isNull(String name) {
		JsonElement element = object.get(name);
		return element == null || element.isJsonNull();
	}

	private JsonElement getElement(String name) {
		JsonElement element = object.get(name);
		if (element == null || element.isJsonNull()) {
			throw new IllegalArgumentException("Object not found: " + name + " in " + object);
		}
		return element;
	}

	private JsonArray getArray(String name) {
		JsonArray array = object.getAsJsonArray(name);
		if (array == null) {
			throw new IllegalArgumentException("Array not found: " + name + " in " + object);
		}
		return array;
	}

	@Override
	public boolean contains(String name) {
		return !isNull(name);
	}

	@Override
	public IJson getObject(String name) {
		return new JsonObjectWrapper(getElement(name).getAsJsonObject());
	}

	@Override
	public String get(String name) {
		return getElement(name).getAsString();
	}

	@Override
	public String get(String name, boolean optional) {
		if (optional && !contains(name)) {
			return null;
		}
		return get(name);
	}

	@Override
	public boolean getBoolean(String name) {
		return getElement(name).getAsBoolean();
	}

	@Override
	public long getLong(String name) {
		return getElement(name).getAsLong();
	}

	@Override
	public int getInt(String name) {
		return getElement(name).getAsInt();
	}

	@Override
	public <E extends Enum<E>> E getEnum(String name, Class<E> enumType) {
		String value = get(name);
		return Enum.valueOf(enumType, value);
	}

	@Override
	public BigDecimal getBigDecimal(String name) {
		return getElement(name).getAsBigDecimal();
	}

	@Override
	public Integer getInt(String name, boolean optional) {
		if (optional && !contains(name)) {
			return null;
		}
		return getInt(name);
	}

	@Override
	public Boolean getBoolean(String name, boolean optional) {
		if (optional && !contains(name)) {
			return null;
		}
		return getBoolean(name);
	}

	@Override
	public BigDecimal getBigDecimal(String name, boolean optional) {
		if (optional && !contains(name)) {
			return null;
		}
		return getBigDecimal(name);
	}

	@Override
	public LocalDateTime getLocalDateTime(String name) {
		String value = get(name);
		return LocalDateTime.parse(value);
	}

	@Override
	public LocalTime getLocalTime(String name) {
		String value = get(name);
		return LocalTime.parse(value);
	}

	@Override
	public List<IJson> getObjectList(String name) {
		List<IJson> list = new ArrayList<>();

		JsonArray array = getArray(name);
		for (JsonElement element : array) {
			list.add(new JsonObjectWrapper(element.getAsJsonObject()));
		}

		return list;
	}

	@Override
	public <E> List<E> getList(String name, Function<IJson, E> function) {
		List<E> list = new ArrayList<>();

		JsonArray array = getArray(name);
		for (JsonElement element : array) {
			list.add(function.apply(new JsonObjectWrapper(element.getAsJsonObject())));
		}

		return list;
	}

}
