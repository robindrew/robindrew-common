package com.robindrew.common.lang.reflect;

import java.lang.reflect.Array;
import java.util.AbstractList;

public class ArrayCollection<E> extends AbstractList<E> {

	private final Object array;
	private final int length;

	public ArrayCollection(Object array) {
		if (!array.getClass().isArray()) {
			throw new IllegalArgumentException("not an array: " + array.getClass());
		}
		this.array = array;
		this.length = Array.getLength(array);
	}

	@Override
	public int size() {
		return length;
	}

	@Override
	@SuppressWarnings("unchecked")
	public E get(int index) {
		return (E) Array.get(array, index);
	}

	@Override
	public E set(int index, E value) {
		E oldValue = get(index);
		Array.set(array, index, value);
		return oldValue;
	}
}
