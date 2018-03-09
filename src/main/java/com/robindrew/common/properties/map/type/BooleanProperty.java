package com.robindrew.common.properties.map.type;

import java.util.Collection;

/**
 * An Boolean Property.
 */
public class BooleanProperty extends AbstractProperty<Boolean> {

	public BooleanProperty(String... keys) {
		super(keys);
	}

	public BooleanProperty(Collection<String> keys) {
		super(keys);
	}

	@Override
	public BooleanProperty notNull(boolean notNull) {
		super.notNull(notNull);
		return this;
	}

	@Override
	public BooleanProperty defaultValue(Boolean value) {
		super.defaultValue(value);
		return this;
	}

	@Override
	protected Boolean parseValue(String key, String value) {
		return Boolean.parseBoolean(value);
	}

}
