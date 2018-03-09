package com.robindrew.sizeof.definition.type;

import com.robindrew.sizeof.IMemoryCalculator;
import com.robindrew.sizeof.definition.ClassDefinition;

/**
 * A Long Array Definition.
 */
public final class LongArrayDefinition extends ClassDefinition<long[]> {

	@Override
	public long sizeOf(long[] array, IMemoryCalculator calculator) {
		return calculator.getArchitecture().sizeOfLongArray(array.length);
	}

}
