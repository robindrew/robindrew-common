package com.robindrew.common.lang;

import java.util.concurrent.atomic.AtomicReference;

import com.robindrew.common.util.Check;

public class Reference<V> implements IReference<V> {

	private final AtomicReference<V> reference = new AtomicReference<>(null);

	public boolean isSet() {
		return reference.get() != null;
	}

	public void set(V value) {
		Check.notNull("value", value);
		if (!reference.compareAndSet(null, value)) {
			throw new IllegalStateException("Value already set in reference");
		}
	}

	@Override
	public V get() {
		V value = reference.get();
		if (value == null) {
			throw new IllegalStateException("Value not set in reference");
		}
		return value;
	}

}
