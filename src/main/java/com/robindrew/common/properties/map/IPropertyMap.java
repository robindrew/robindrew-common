package com.robindrew.common.properties.map;

import java.util.Map;
import java.util.Properties;

/**
 * A thread-safe read-only view on properties.
 */
public interface IPropertyMap {

	/**
	 * Returns the source of these properties.
	 */
	String getSource();

	/**
	 * Returns all the underlying properties as properties.
	 * @param forDisplay indicates if the properties as for display and should be obfuscated.
	 */
	Properties asProperties(boolean forDisplay);

	/**
	 * Returns all the underlying properties as a map.
	 * @param forDisplay indicates if the properties as for display and should be obfuscated.
	 */
	Map<String, String> asMap(boolean forDisplay);

	/**
	 * Returns all the underlying properties as a source map.
	 * @param forDisplay indicates if the properties as for display and should be obfuscated.
	 */
	Map<String, SourceProperty> asSourceMap(boolean forDisplay);

	/**
	 * Returns true if this map contains the give property key.
	 */
	boolean contains(String key);

	/**
	 * Returns the value associated with the given key or throw an exception if none.
	 */
	String get(String key);

	/**
	 * Returns the value associated with the given key or the given default value if none..
	 */
	String get(String key, String defaultValue);

	/**
	 * Returns true if this map is empty.
	 */
	boolean isEmpty();

}
