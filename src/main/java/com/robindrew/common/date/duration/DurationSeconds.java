package com.robindrew.common.date.duration;

import java.util.concurrent.TimeUnit;

import com.robindrew.common.util.Java;

/**
 * A Duration (in seconds).
 * @author Robin Drew
 */
public class DurationSeconds extends Duration {

	/**
	 * Creates a new duration.
	 * @param duration the duration (in seconds).
	 */
	public DurationSeconds(long duration) {
		super(duration);
	}

	/**
	 * Creates a new duration.
	 * @param timestamp1 the first timestamp (in seconds).
	 * @param timestamp2 the second timestamp (in seconds).
	 */
	public DurationSeconds(long timestamp1, long timestamp2) {
		super(timestamp1, timestamp2);
	}

	@Override
	public long getInstant() {
		return Java.currentTimeSeconds();
	}

	@Override
	public TimeUnit getTimeUnit() {
		return TimeUnit.SECONDS;
	}
}
