package com.robindrew.common.collect.count;

public class CountEntry<E> implements ICountEntry<E> {

	private final E key;
	private final long value;

	public CountEntry(E key, long value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public E getKey() {
		return key;
	}

	@Override
	public long getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value + "x" + key;
	}
}
