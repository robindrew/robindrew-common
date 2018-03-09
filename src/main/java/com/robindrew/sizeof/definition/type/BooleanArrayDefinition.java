package com.robindrew.sizeof.definition.type;

import com.robindrew.sizeof.IMemoryCalculator;
import com.robindrew.sizeof.definition.ClassDefinition;

/**
 * A Boolean Array Definition.
 */
public final class BooleanArrayDefinition extends ClassDefinition<boolean[]> {

	@Override
	public long sizeOf(boolean[] array, IMemoryCalculator calculator) {
		return calculator.getArchitecture().sizeOfBooleanArray(array.length);
	}
}
