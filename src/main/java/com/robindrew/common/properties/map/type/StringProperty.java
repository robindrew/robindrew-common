package com.robindrew.common.properties.map.type;

import java.util.Collection;
import java.util.regex.Pattern;

import com.robindrew.common.properties.map.PropertyException;

/**
 * A String Property.
 */
public class StringProperty extends AbstractProperty<String> {

	/** The minimum length. */
	private int minimumLength = 0;
	/** The maximum length. */
	private int maximumLength = Integer.MAX_VALUE;
	/** The pattern. */
	private Pattern pattern = null;

	/**
	 * Creates the property.
	 * @param keys the keys.
	 */
	public StringProperty(String... keys) {
		super(keys);
	}

	/**
	 * Creates the property.
	 * @param keys the keys.
	 */
	public StringProperty(Collection<String> keys) {
		super(keys);
	}

	public StringProperty min(int minimumLength) {
		this.minimumLength = minimumLength;
		return this;
	}

	public StringProperty max(int maximumLength) {
		this.maximumLength = maximumLength;
		return this;
	}

	public StringProperty pattern(Pattern pattern) {
		this.pattern = pattern;
		return this;
	}

	public StringProperty pattern(String regex) {
		return pattern(Pattern.compile(regex));
	}

	@Override
	public StringProperty notNull(boolean notNull) {
		super.notNull(notNull);
		return this;
	}

	@Override
	public StringProperty defaultValue(String value) {
		super.defaultValue(value);
		return this;
	}

	public int getMinimumLength() {
		return minimumLength;
	}

	public int getMaximumLength() {
		return maximumLength;
	}

	public Pattern getPattern() {
		return pattern;
	}

	@Override
	protected String parseValue(String key, String value) {
		int length = value.length();
		if (length < minimumLength) {
			throw new PropertyException(toString(key, value) + " less than minimum length: " + minimumLength);
		}
		if (length > maximumLength) {
			throw new PropertyException(toString(key, value) + " greater than maximum length: " + maximumLength);
		}
		if (pattern != null) {
			if (!pattern.matcher(value).matches()) {
				throw new PropertyException(toString(key, value) + " not matching pattern: '" + pattern.pattern() + "'");
			}
		}
		return value;
	}

}
