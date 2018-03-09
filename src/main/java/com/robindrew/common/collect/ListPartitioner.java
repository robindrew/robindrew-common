package com.robindrew.common.collect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;

public class ListPartitioner<E> {

	private final BiPredicate<E, E> predicate;

	public ListPartitioner(BiPredicate<E, E> predicate) {
		if (predicate == null) {
			throw new NullPointerException("comparator");
		}
		this.predicate = predicate;
	}

	public List<List<E>> partition(Collection<? extends E> collection) {
		if (collection.isEmpty()) {
			return Collections.emptyList();
		}
		if (collection.size() == 1) {
			return Arrays.asList(new ArrayList<>(collection));
		}

		List<List<E>> partitions = new ArrayList<>();

		LinkedList<E> linked = new LinkedList<>(collection);
		while (!linked.isEmpty()) {
			List<E> partition = removePartition(linked);
			partitions.add(partition);
		}

		return partitions;
	}

	private List<E> removePartition(LinkedList<E> linked) {
		List<E> partition = new ArrayList<>();

		E element = linked.removeFirst();
		partition.add(element);

		Iterator<E> iterator = linked.iterator();
		while (iterator.hasNext()) {
			E next = iterator.next();
			if (predicate.test(element, next)) {
				iterator.remove();
				partition.add(element);
			}
		}

		return partition;
	}

}
