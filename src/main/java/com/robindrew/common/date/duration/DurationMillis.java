package com.robindrew.common.date.duration;

import java.util.concurrent.TimeUnit;

/**
 * A Duration (in milliseconds).
 * @author Robin Drew
 */
public class DurationMillis extends Duration {

	/**
	 * Creates a new duration.
	 * @param duration the duration (in millis).
	 */
	public DurationMillis(long duration) {
		super(duration);
	}

	/**
	 * Creates a new duration.
	 * @param timestamp1 the first timestamp (in millis).
	 * @param timestamp2 the second timestamp (in millis).
	 */
	public DurationMillis(long timestamp1, long timestamp2) {
		super(timestamp1, timestamp2);
	}

	@Override
	public long getInstant() {
		return System.currentTimeMillis();
	}

	@Override
	public TimeUnit getTimeUnit() {
		return TimeUnit.MILLISECONDS;
	}

	/**
	 * Returns this time in minutes.
	 * @return this time in minutes.
	 */
	public long getMinutes() {
		return getTimeUnit().toMinutes(getDuration());
	}

	/**
	 * Returns this time in hours.
	 * @return this time in hours.
	 */
	public long getHours() {
		return getTimeUnit().toHours(getDuration());
	}

	/**
	 * Returns this time in days.
	 * @return this time in days.
	 */
	public long getDays() {
		return getTimeUnit().toDays(getDuration());
	}

}
