package com.robindrew.sizeof.definition.type;

import com.robindrew.sizeof.IMemoryCalculator;
import com.robindrew.sizeof.definition.ClassDefinition;

/**
 * A Double Array Definition.
 */
public final class DoubleArrayDefinition extends ClassDefinition<double[]> {

	@Override
	public long sizeOf(double[] array, IMemoryCalculator calculator) {
		return calculator.getArchitecture().sizeOfDoubleArray(array.length);
	}

}
