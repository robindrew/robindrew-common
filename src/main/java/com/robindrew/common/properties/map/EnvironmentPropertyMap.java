package com.robindrew.common.properties.map;

import java.util.Map;

public class EnvironmentPropertyMap extends AbstractPropertyMap {

	public EnvironmentPropertyMap() {
		super("Environment");
	}

	@Override
	protected String getProperty(String key) {
		return System.getenv(key);
	}

	@Override
	public Map<String, SourceProperty> asSourceMap(boolean forDisplay) {
		return toMap("Environment", System.getenv(), forDisplay);
	}

	@Override
	public boolean isEmpty() {
		return System.getenv().isEmpty();
	}
}
