package com.robindrew.sizeof.definition.type;

import com.robindrew.sizeof.IMemoryCalculator;
import com.robindrew.sizeof.definition.ClassDefinition;

/**
 * A Boolean Array Definition.
 */
public final class ObjectArrayDefinition extends ClassDefinition<Object[]> {

	@Override
	public long sizeOf(Object[] array, IMemoryCalculator calculator) {
		return calculator.getArchitecture().sizeOfObjectArray(array.length);
	}

	@Override
	public final boolean hasElements(Object[] array) {
		return array.length > 0;
	}

	@Override
	public long sizeOfElements(Object[] array, IMemoryCalculator calculator, long size) {
		for (Object element : array) {
			if (element != null) {
				size += calculator.sizeOf(element);
			}
		}
		return size;
	}
}
