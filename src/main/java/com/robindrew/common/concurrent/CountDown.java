package com.robindrew.common.concurrent;

import com.robindrew.common.util.Java;

public class CountDown implements ICountDown {

	private volatile long count = 0;

	@Override
	public void increment() {
		synchronized (this) {
			count++;
		}
	}

	@Override
	public void decrement() {
		synchronized (this) {
			if (count == 0) {
				throw new IllegalStateException("count already zero");
			}
			count--;
			if (count == 0) {
				notify();
			}
		}
	}

	@Override
	public long get() {
		synchronized (this) {
			return count;
		}
	}

	@Override
	public void waitFor() {
		try {
			synchronized (this) {
				while (count > 0) {
					wait();
				}
			}
		} catch (InterruptedException e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public boolean waiting() {
		return get() > 0;
	}

	@Override
	public void set(long count) {
		if (count < 0) {
			throw new IllegalArgumentException("count=" + count);
		}
		synchronized (this) {
			this.count = count;
			if (this.count == 0) {
				notify();
			}
		}
	}

}
