package com.robindrew.common.properties.map;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.util.Check;

/**
 * A thread-safe read/write view on properties.
 */
public abstract class MutablePropertyMap extends AbstractPropertyMap {

	private static final Logger log = LoggerFactory.getLogger(MutablePropertyMap.class);

	protected MutablePropertyMap(String source) {
		super(source);
	}

	public void addAll(String sourceName, Map<String, String> map) {
		log.info("[Adding Properties] {}", sourceName);
		for (Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			add(key, value);
		}
	}

	private void add(String key, String value) {
		Check.notNull("key", key);
		Check.notNull("value", value);

		// We should signal when a property has been overridden by another
		if (addProperty(key, value) == null) {
			log.info("[Property] {} = {}", key, getDisplayValue(key, value));
		} else {
			log.info("[Override] {} = {}", key, getDisplayValue(key, value));
		}
	}

	protected abstract String addProperty(String key, String value);

}
