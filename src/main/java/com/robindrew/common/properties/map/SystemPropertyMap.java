package com.robindrew.common.properties.map;

import java.util.Map;

public class SystemPropertyMap extends MutablePropertyMap {

	public SystemPropertyMap() {
		super("System");
	}

	@Override
	protected String getProperty(String key) {
		return System.getProperty(key);
	}

	@Override
	public Map<String, SourceProperty> asSourceMap(boolean forDisplay) {
		return toMap("System", System.getProperties(), forDisplay);
	}

	@Override
	protected String addProperty(String key, String value) {
		return System.setProperty(key, value);
	}

	@Override
	public boolean isEmpty() {
		return System.getProperties().isEmpty();
	}
}
