package com.robindrew.common.properties.map.type;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.common.base.Objects;
import com.robindrew.common.properties.map.IPropertyMap;
import com.robindrew.common.properties.map.PropertyException;
import com.robindrew.common.properties.map.PropertyMap;
import com.robindrew.common.util.Check;

/**
 * An Abstract Property.
 */
public abstract class AbstractProperty<V> implements IProperty<V> {

	public static String toString(String key, String value) {
		return "key: '" + key + "', value: '" + value + "'";
	}

	/** The property key. */
	private final Set<String> keys;
	/** The current value. */
	private volatile V currentValue;
	/** The default value. */
	private volatile V defaultValue;
	/** Indicates if null is a valid value. */
	private boolean notNull = true;
	/** Indicates if this is optional. */
	private boolean optional = false;

	protected AbstractProperty(String... keys) {
		this(Arrays.asList(keys));
	}

	protected AbstractProperty(Collection<String> keys) {
		Check.notEmptyAndElementsNotNull("keys", keys);
		this.keys = new LinkedHashSet<String>(keys);
	}

	@Override
	public IProperty<V> optional() {
		this.optional = true;
		return this;
	}

	public final Set<String> getKeys() {
		return Collections.unmodifiableSet(keys);
	}

	public AbstractProperty<V> notNull(boolean notNull) {
		this.notNull = notNull;
		return this;
	}

	public AbstractProperty<V> defaultValue(V value) {
		if (notNull) {
			Check.notNull("value", value);
		}
		this.defaultValue = value;
		return this;
	}

	@Override
	public boolean hasValue(V value) {
		return Objects.equal(get(), value);
	}

	@Override
	public V get() {
		if (currentValue != null) {
			return currentValue;
		}

		IPropertyMap map = PropertyMap.getPropertyMap();
		for (String key : keys) {
			String value = map.get(key, null);
			if (value != null) {
				currentValue = parseValue(key, value);
				return currentValue;
			}
		}

		// Not found, use default value if set
		if (defaultValue != null) {
			return defaultValue;
		}

		// Not found, is null valid?
		if (!notNull) {
			return null;
		}

		// Optional?
		if (optional) {
			return null;
		}

		// Default handling of not found cases
		throw new PropertyException("Property Not Found: " + keys);
	}

	@Override
	public V get(V defaultValue) {
		if (currentValue != null) {
			return currentValue;
		}

		IPropertyMap map = PropertyMap.getPropertyMap();
		for (String key : keys) {
			String value = map.get(key, null);
			if (value != null) {
				currentValue = parseValue(key, value);
				return currentValue;
			}
		}

		// Not found, use default value
		return defaultValue;
	}

	@Override
	public String getString() {
		return String.valueOf(get());
	}

	@Override
	public boolean exists() {
		if (currentValue != null) {
			return true;
		}

		IPropertyMap map = PropertyMap.getPropertyMap();
		for (String key : keys) {
			if (map.contains(key)) {
				String value = map.get(key);
				currentValue = parseValue(key, value);
				return true;
			}
		}

		// Not exists
		return false;
	}

	/**
	 * Returns the value.
	 * @param key the key.
	 * @param value the value.
	 * @return the value.
	 */
	protected abstract V parseValue(String key, String value);

}
