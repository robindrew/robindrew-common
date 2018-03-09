package com.robindrew.sizeof.definition.type;

import com.robindrew.sizeof.IMemoryCalculator;
import com.robindrew.sizeof.definition.ClassDefinition;

/**
 * A Float Array Definition.
 */
public final class FloatArrayDefinition extends ClassDefinition<float[]> {

	@Override
	public long sizeOf(float[] array, IMemoryCalculator calculator) {
		return calculator.getArchitecture().sizeOfFloatArray(array.length);
	}

}
