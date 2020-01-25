package com.robindrew.common.template;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Multimap;

public class TemplateData implements ITemplateData {

	private final Map<String, Object> map;

	public TemplateData(Map<String, ?> map) {
		if (map == null) {
			throw new NullPointerException("map");
		}
		this.map = new LinkedHashMap<String, Object>(map);
	}

	public TemplateData(Multimap<String, ?> map) {
		if (map == null) {
			throw new NullPointerException("map");
		}
		this.map = new LinkedHashMap<String, Object>(map.size());
		for (Entry<String, ?> entry : map.entries()) {
			this.map.put(entry.getKey(), entry.getValue());
		}
	}

	public TemplateData() {
		this.map = new LinkedHashMap<String, Object>();
	}

	@Override
	public Object get(String key) {
		Object value = get(key, map);
		if (value == null) {
			value = getElement(key);
		}
		if (value == null) {
			throw new TemplateException("Variable not found: '" + key + "' in map: " + map);
		}
		return value;
	}

	private Object getElement(String key) {
		int index1 = key.indexOf('[');
		if (index1 == -1) {
			return null;
		}
		int index2 = key.indexOf(']', index1 + 1);
		if (index2 == -1) {
			return null;
		}

		// Element object
		String keyIndex = key.substring(index1 + 1, index2);
		key = key.substring(0, index1);
		Object value = get(key, map);
		if (value == null) {
			throw new TemplateException("Variable not found: '" + key + "' in map: " + map);
		}

		// List
		if (value instanceof List) {
			int index = Integer.parseInt(keyIndex);
			List<?> list = (List<?>) value;
			return list.get(index);
		}

		// Array
		if (value.getClass().isArray()) {
			int index = Integer.parseInt(keyIndex);
			return Array.get(value, index);

		}

		// Map
		if (value instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) value;
			return map.get(keyIndex);
		}

		// Array
		throw new TemplateException("Variable not supported: '" + key + "', type=" + value.getClass());
	}

	private Object get(String key, Map<String, Object> map) {
		Object value = map.get(key);
		if (value == null) {
			value = getObject(key, map);
			if (value == null) {
				return null;
			}
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	private Object getObject(String key, Map<String, Object> map) {
		String[] parts = key.split("\\.");
		if (parts.length < 2) {
			return null;
		}
		Object object = map.get(parts[0]);
		if (object == null) {
			return null;
		}
		if (object instanceof Map) {
			String subKey = key.substring(key.indexOf('.') + 1);
			Map<String, Object> subMap = (Map<String, Object>) object;
			return get(subKey, subMap);
		}
		for (int i = 1; i < parts.length; i++) {
			object = getValue(object, parts[i]);
		}
		return object;
	}

	private Object getValue(Object object, String fieldName) {
		try {
			char first = Character.toUpperCase(fieldName.charAt(0));
			fieldName = "get" + first + fieldName.substring(1);
			Method method = object.getClass().getDeclaredMethod(fieldName, new Class[0]);
			return method.invoke(object, new Object[0]);
		} catch (Exception e) {
			throw new TemplateException("Unable to execute method: " + fieldName + "() on object " + object, e);
		}
	}

	@Override
	public boolean contains(String key) {
		Object value = get(key, map);
		if (value == null) {
			value = getElement(key);
		}
		return value != null;
	}

	@Override
	public void set(String key, Object value) {
		if (key.isEmpty()) {
			throw new IllegalArgumentException("key is empty");
		}
		if (value == null) {
			throw new NullPointerException("value");
		}
		map.put(key, value);
	}

	@Override
	public void set(String key, Object value, Object defaultValue) {
		if (key.isEmpty()) {
			throw new IllegalArgumentException("key is empty");
		}
		if (defaultValue == null) {
			throw new NullPointerException("defaultValue");
		}
		if (value == null) {
			value = defaultValue;
		}
		map.put(key, value);
	}

	@Override
	public void remove(String key) {
		map.remove(key);
	}

	@Override
	public Map<String, Object> toMap() {
		return Collections.unmodifiableMap(map);
	}

}
