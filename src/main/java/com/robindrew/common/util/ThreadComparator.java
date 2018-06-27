package com.robindrew.common.util;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class ThreadComparator implements Comparator<Thread> {

	@Override
	public int compare(Thread thread1, Thread thread2) {
		CompareToBuilder compare = new CompareToBuilder();
		compare.append(thread1.getId(), thread2.getId());
		compare.append(thread1.getName(), thread2.getName());
		compare.append(thread1.getState(), thread2.getState());
		return compare.toComparison();
	}

}
