package com.robindrew.common.lang;

import com.google.common.base.Supplier;

public interface IReference<V> extends Supplier<V> {

	boolean isSet();

	void set(V value);

}
