package com.robindrew.common.template;

import java.util.Map;

public interface ITemplateData {

	Object get(String key);

	void set(String key, Object value);

	void set(String key, Object value, Object defaultValue);

	boolean contains(String key);

	void remove(String key);

	Map<String, Object> toMap();

}
