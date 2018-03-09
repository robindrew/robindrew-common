package com.robindrew.common.collect.copyonwrite;

import static java.util.Collections.emptySet;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class CopyOnWriteSet<E> implements Set<E> {

	private volatile Set<E> set = emptySet();

	protected Set<E> newSet(Set<E> set) {
		// Override to create different sets
		return new LinkedHashSet<E>(set);
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public boolean equals(Object object) {
		return set.equals(object);
	}

	@Override
	public int hashCode() {
		return set.hashCode();
	}

	@Override
	public boolean contains(Object element) {
		return set.contains(element);
	}

	@Override
	public boolean containsAll(Collection<?> elements) {
		return set.containsAll(elements);
	}

	@Override
	public Iterator<E> iterator() {
		return set.iterator();
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@Override
	public <T> T[] toArray(T[] array) {
		return set.toArray(array);
	}

	@Override
	public boolean remove(Object element) {
		synchronized (this) {
			Set<E> newSet = newSet(set);
			boolean returnValue = newSet.remove(element);
			this.set = newSet;
			return returnValue;
		}
	}

	@Override
	public boolean add(E element) {
		synchronized (this) {
			Set<E> newSet = newSet(set);
			boolean returnValue = newSet.add(element);
			this.set = newSet;
			return returnValue;
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> elements) {
		synchronized (this) {
			Set<E> newSet = newSet(set);
			boolean returnValue = newSet.addAll(elements);
			this.set = newSet;
			return returnValue;
		}
	}

	@Override
	public boolean retainAll(Collection<?> elements) {
		synchronized (this) {
			Set<E> newSet = newSet(set);
			boolean returnValue = newSet.retainAll(elements);
			this.set = newSet;
			return returnValue;
		}
	}

	@Override
	public boolean removeAll(Collection<?> elements) {
		synchronized (this) {
			Set<E> newSet = newSet(set);
			boolean returnValue = newSet.removeAll(elements);
			this.set = newSet;
			return returnValue;
		}
	}

	@Override
	public void clear() {
		synchronized (this) {
			this.set = newSet(emptySet());
		}
	}

}
