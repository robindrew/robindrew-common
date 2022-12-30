package com.robindrew.common.date.time;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicTimeCounter implements ITimeCounter {

	private final AtomicInteger totalNumber = new AtomicInteger(0);
	private final AtomicLong totalTime = new AtomicLong(0);

	@Override
	public void increment(long time) {
		if (time < 0) {
			throw new IllegalArgumentException();
		}
		totalNumber.addAndGet(1);
		totalTime.addAndGet(time);
	}

	@Override
	public int getNumber() {
		return totalNumber.get();
	}

	@Override
	public long getTotalTime() {
		return totalTime.get();
	}

	@Override
	public long getAverageTime() {
		long number = getNumber();
		long time = getTotalTime();
		if (number == 0) {
			return 0;
		}
		return time / number;
	}

	@Override
	public void reset() {
		totalNumber.set(0);
		totalTime.set(0);
	}

}
