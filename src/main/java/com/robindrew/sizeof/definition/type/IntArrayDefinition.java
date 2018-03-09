package com.robindrew.sizeof.definition.type;

import com.robindrew.sizeof.IMemoryCalculator;
import com.robindrew.sizeof.definition.ClassDefinition;

/**
 * A Int Array Definition.
 */
public final class IntArrayDefinition extends ClassDefinition<int[]> {

	@Override
	public long sizeOf(int[] array, IMemoryCalculator calculator) {
		return calculator.getArchitecture().sizeOfIntArray(array.length);
	}

}
