package com.robindrew.common.collect.capacity;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * A fixed-memory, capacity limited list. Uses a rolling index on a fixed size array to avoid any object allocation.
 * Does not support removal of objects.
 */
public class ArrayCapacityList<E> extends AbstractCollection<E> implements ICapacityList<E> {

	private final int capacity;
	private final Object[] array;
	private int offset = 0;
	private int size = 0;

	public ArrayCapacityList(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("capacity=" + capacity);
		}
		this.capacity = capacity;
		this.array = new Object[capacity];
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		offset = 0;
		size = 0;
		for (int i = 0; i < array.length; i++) {
			array[i] = null;
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> elements) {
		boolean changed = false;
		for (E element : elements) {
			if (add(element)) {
				changed = true;
			}
		}
		return changed;
	}

	@Override
	public boolean add(E element) {
		if (offset == capacity) {
			offset = 0;
		}
		array[offset++] = element;
		if (size < capacity) {
			size++;
		}
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<E> toList() {
		List<E> list = new ArrayList<>();
		int index = (size < capacity) ? 0 : offset;
		int count = size;
		while (count > 0) {
			if (index == capacity) {
				index = 0;
			}
			list.add((E) array[index++]);
			count--;
		}
		return list;
	}

	@Override
	public boolean remove(Object object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<E> iterator() {
		return toList().iterator();
	}

}
