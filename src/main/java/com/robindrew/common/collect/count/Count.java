package com.robindrew.common.collect.count;

public class Count {

	private long count = 0;

	public Count() {
	}

	public Count(int count) {
		if (count < 0) {
			throw new IllegalArgumentException("count=" + count);
		}
		this.count = count;
	}

	public long get() {
		return count;
	}

	public long increment() {
		return increment(1);
	}

	public long increment(long amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("amount=" + amount);
		}
		this.count += amount;
		return count;
	}

	public long decrement() {
		return decrement(1);
	}

	public long decrement(long amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("amount=" + amount);
		}
		this.count += amount;
		return count;
	}

	@Override
	public String toString() {
		return String.valueOf(count);
	}

}
