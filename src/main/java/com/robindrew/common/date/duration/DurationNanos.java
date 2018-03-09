package com.robindrew.common.date.duration;

import java.util.concurrent.TimeUnit;

/**
 * A Duration (in nanoseconds).
 * @author Robin Drew
 */
public class DurationNanos extends Duration {

	/**
	 * Creates a new duration.
	 * @param duration the duration (in nanos).
	 */
	public DurationNanos(long duration) {
		super(duration);
	}

	/**
	 * Creates a new duration.
	 * @param timestamp1 the first timestamp (in nanos).
	 * @param timestamp2 the second timestamp (in nanos).
	 */
	public DurationNanos(long timestamp1, long timestamp2) {
		super(timestamp1, timestamp2);
	}

	@Override
	public long getInstant() {
		return System.nanoTime();
	}

	@Override
	public TimeUnit getTimeUnit() {
		return TimeUnit.NANOSECONDS;
	}
}
