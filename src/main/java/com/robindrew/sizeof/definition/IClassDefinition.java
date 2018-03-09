package com.robindrew.sizeof.definition;

import com.robindrew.sizeof.IMemoryCalculator;

/**
 * A Class Definition.
 * <p>
 * Defines how to calculate the size of particular object type. Implement definitions for specific types to optimise or
 * correct the basic algorithm.
 * </p>
 */
public interface IClassDefinition<D> {

	long sizeOf(D instance, IMemoryCalculator calculator);

	boolean hasElements(D instance);

	long sizeOfElements(D instance, IMemoryCalculator calculator, long size);

}
