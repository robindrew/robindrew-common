package com.robindrew.common.properties.map;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;

import com.robindrew.common.text.Strings;
import com.robindrew.common.util.Check;

public abstract class AbstractPropertyMap implements IPropertyMap {

	public final Map<String, SourceProperty> toMap(String source, Map<? extends Object, ? extends Object> map, boolean forDisplay) {
		Map<String, SourceProperty> sourceMap = new LinkedHashMap<>();
		for (Entry<? extends Object, ? extends Object> entry : map.entrySet()) {
			String key = Strings.valueOf(entry.getKey(), null);
			String value = Strings.valueOf(entry.getValue(), null);
			if (forDisplay) {
				value = getDisplayValue(key, value);
			}
			sourceMap.put(key, new SourceProperty(source, key, value));
		}
		return sourceMap;
	}

	private final String source;

	protected AbstractPropertyMap(String source) {
		this.source = Check.notNull("source", source);
	}

	@Override
	public String getSource() {
		return source;
	}

	@Override
	public boolean contains(String key) {
		return getProperty(key) != null;
	}

	@Override
	public String get(String key) {
		Check.notNull("key", key);

		// Mandatory get method
		String value = getProperty(key);
		if (value == null) {
			throw new IllegalArgumentException("property not found: '" + key + "'");
		}
		return value;
	}

	@Override
	public String get(String key, String defaultValue) {
		Check.notNull("key", key);

		// Mandatory get method
		String value = getProperty(key);
		if (value == null) {

			// The default value can be anything, even null
			return defaultValue;
		}
		return value;
	}

	@Override
	public Properties asProperties(boolean forDisplay) {
		Map<String, SourceProperty> sourceMap = asSourceMap(forDisplay);

		// Build the properties
		Properties properties = new Properties();
		for (Entry<String, SourceProperty> entry : sourceMap.entrySet()) {
			properties.put(entry.getKey(), entry.getValue().getValue());
		}
		return properties;
	}

	@Override
	public Map<String, String> asMap(boolean forDisplay) {
		Map<String, SourceProperty> sourceMap = asSourceMap(forDisplay);

		// Build the map
		Map<String, String> map = new TreeMap<>();
		for (Entry<String, SourceProperty> entry : sourceMap.entrySet()) {
			map.put(entry.getKey(), entry.getValue().getValue());
		}
		return map;
	}

	/**
	 * Returns the display value for the given key/value pair.
	 */
	protected String getDisplayValue(String key, String value) {
		if (hideValue(key) || hideValue(value)) {
			return "HIDDEN";
		}
		return value;
	}

	/**
	 * Override this method to provide a more appropriate way to detect passwords and secure information to obfuscate.
	 */
	protected boolean hideValue(String value) {
		return value.toLowerCase().contains("pass");
	}

	protected abstract String getProperty(String key);
}
