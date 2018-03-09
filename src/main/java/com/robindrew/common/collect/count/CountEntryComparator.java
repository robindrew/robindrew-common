package com.robindrew.common.collect.count;

import java.util.Comparator;

public class CountEntryComparator<E> implements Comparator<ICountEntry<E>> {

	private final boolean reverse;

	public CountEntryComparator() {
		this(false);
	}

	public CountEntryComparator(boolean reverse) {
		this.reverse = reverse;
	}

	@Override
	public int compare(ICountEntry<E> entry1, ICountEntry<E> entry2) {
		long value1 = entry1.getValue();
		long value2 = entry2.getValue();
		int compare = (value1 < value2 ? -1 : (value1 == value2 ? 0 : 1));
		return reverse ? -compare : compare;
	}

}
