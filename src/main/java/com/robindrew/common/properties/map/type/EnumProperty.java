package com.robindrew.common.properties.map.type;

import java.util.Collection;

/**
 * An {@link Enum} Property.
 */
public class EnumProperty<E extends Enum<E>> extends AbstractProperty<E> {

	private final Class<E> enumClass;

	public EnumProperty(Class<E> enumClass, String... keys) {
		super(keys);
		this.enumClass = enumClass;
	}

	public EnumProperty(Class<E> enumClass, Collection<String> keys) {
		super(keys);
		this.enumClass = enumClass;
	}

	@Override
	public EnumProperty<E> notNull(boolean notNull) {
		super.notNull(notNull);
		return this;
	}

	@Override
	public EnumProperty<E> defaultValue(E value) {
		super.defaultValue(value);
		return this;
	}

	@Override
	protected E parseValue(String key, String value) {
		return Enum.valueOf(enumClass, value);
	}

}
