package com.robindrew.sizeof.definition.type;

import com.robindrew.sizeof.IMemoryCalculator;
import com.robindrew.sizeof.definition.ClassDefinition;

/**
 * A Short Array Definition.
 */
public final class ShortArrayDefinition extends ClassDefinition<short[]> {

	@Override
	public long sizeOf(short[] array, IMemoryCalculator calculator) {
		return calculator.getArchitecture().sizeOfShortArray(array.length);
	}

}
