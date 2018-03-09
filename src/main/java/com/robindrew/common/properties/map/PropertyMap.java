package com.robindrew.common.properties.map;

import static com.robindrew.common.properties.map.CompositePropertyMap.newComposite;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.robindrew.common.util.Check;

public class PropertyMap extends MutablePropertyMap {

	/** The global properties. */
	private static volatile IPropertyMap globalProperties;

	/**
	 * So that property lookups work immediately we create a default property map that is a composite of System and
	 * Environment properties.
	 */
	static {
		globalProperties = newComposite("Global").withSystem().withEnv();
	}

	/**
	 * Returns the global property map (typically a composite).
	 */
	public static IPropertyMap getPropertyMap() {
		return globalProperties;
	}

	/**
	 * Sets the global property map. Typically this method is used to replace the default property map which consists of
	 * System and Environment properties, with a composite that includes properties loaded from file(s).
	 */
	public static void setPropertyMap(IPropertyMap map) {
		globalProperties = Check.notNull("map", map);
	}

	/** The underlying thread-safe properties map. */
	private final Map<String, String> properties = new ConcurrentHashMap<>();

	public PropertyMap(String source) {
		super(source);
	}

	public PropertyMap(String source, Map<String, String> properties) {
		super(source);
		this.properties.putAll(properties);
	}

	@Override
	protected String getProperty(String key) {
		return properties.get(key);
	}

	@Override
	protected String addProperty(String key, String value) {
		return properties.put(key, value);
	}

	@Override
	public Map<String, SourceProperty> asSourceMap(boolean forDisplay) {
		return toMap(getSource(), properties, forDisplay);
	}

	@Override
	public boolean isEmpty() {
		return properties.isEmpty();
	}
}
