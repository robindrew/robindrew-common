package com.robindrew.sizeof.definition;

import java.lang.reflect.Field;

import com.robindrew.sizeof.IMemoryCalculator;
import com.robindrew.sizeof.SizeOfException;

/**
 * A Class Definition.
 */
public abstract class ClassDefinition<T> implements IClassDefinition<T> {

	@SuppressWarnings("unchecked")
	protected static final <T> T get(Field field, Object instance) {
		try {
			return (T) field.get(instance);
		} catch (Exception e) {
			throw SizeOfException.rethrow(e);
		}
	}

	/**
	 * Returns true if this has elements.
	 * @param instance the instance.
	 * @return true if this has elements.
	 */
	@Override
	public boolean hasElements(T instance) {
		return false;
	}

	/**
	 * Returns an element.
	 * @param instance the instance.
	 * @param calculator the calculator.
	 * @param size the size.
	 * @return the element.
	 */
	@Override
	public long sizeOfElements(T instance, IMemoryCalculator calculator, long size) {
		throw new IllegalStateException("no elements");
	}

}
