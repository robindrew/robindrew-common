package com.robindrew.sizeof;

import java.util.Set;

import com.robindrew.sizeof.architecture.IArchitecture;
import com.robindrew.sizeof.definition.IClassDefinitionMap;

public interface IMemoryCalculator {

	/**
	 * Returns the architecture used in calculations.
	 * @return the architecture used in calculations.
	 */
	IArchitecture getArchitecture();

	/**
	 * Returns the map of type definitions.
	 * @return the map of type definitions.
	 */
	IClassDefinitionMap getDefinitionMap();

	/**
	 * Returns the set of instances already calculated.
	 * @return the set of instances already calculated.
	 */
	Set<Object> getInstanceSet();

	/**
	 * Calculate and return the size of the given object.
	 * @param <T> the object type.
	 * @param object the object to calculate the size of.
	 * @return the size of the object in bytes.
	 */
	<T> long sizeOf(T object);

}
