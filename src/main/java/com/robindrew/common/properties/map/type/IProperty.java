package com.robindrew.common.properties.map.type;

import com.google.common.base.Supplier;

public interface IProperty<V> extends Supplier<V> {

	boolean hasValue(V value);

	boolean exists();

	IProperty<V> optional();

	String getString();

	V get(V defaultValue);

}
