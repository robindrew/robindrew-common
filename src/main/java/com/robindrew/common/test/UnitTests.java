package com.robindrew.common.test;

public class UnitTests {

	public static final String getProperty(String key) {
		String value = System.getProperty(key);
		if (value == null) {
			throw new IllegalArgumentException("Mandatory system property missing: '" + key + "'");
		}
		return value;
	}

	public static final String getProperty(String key, String defaultValue) {
		String value = System.getProperty(key);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

}
