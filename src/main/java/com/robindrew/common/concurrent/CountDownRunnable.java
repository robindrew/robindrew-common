package com.robindrew.common.concurrent;

public abstract class CountDownRunnable implements Runnable {

	private CountDown count;

	public CountDownRunnable(CountDown count) {
		if (count == null) {
			throw new NullPointerException("count");
		}
		this.count = count;
		count.increment();
	}

	@Override
	public void run() {
		try {
			runCount();
		} finally {
			count.decrement();
		}
	}

	public abstract void runCount();

}
