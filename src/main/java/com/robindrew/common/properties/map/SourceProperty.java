package com.robindrew.common.properties.map;

import com.robindrew.common.util.Check;

public class SourceProperty {

	private final String source;
	private final String key;
	private final String value;

	public SourceProperty(String source, String key, String value) {
		this.source = Check.notEmpty("source", source);
		this.key = Check.notNull("source", source);
		this.value = Check.notNull("source", source);
	}

	public String getSource() {
		return source;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
