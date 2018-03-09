package com.robindrew.common.properties;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Properties {

	public static final Map<String, String> toMap(java.util.Properties properties) {
		Map<String, String> map = new TreeMap<>();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			map.put((String) key, (String) value);
		}
		return map;
	}

}
