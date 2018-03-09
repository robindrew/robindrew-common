package com.robindrew.sizeof.definition.type;

import com.robindrew.sizeof.IMemoryCalculator;
import com.robindrew.sizeof.definition.ClassDefinition;

/**
 * An Ignore Definition.
 */
public final class IgnoreDefinition<T> extends ClassDefinition<T> {

	@Override
	public long sizeOf(T instance, IMemoryCalculator calculator) {
		return 0;
	}

}
