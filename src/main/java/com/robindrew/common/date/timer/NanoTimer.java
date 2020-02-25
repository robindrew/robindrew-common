package com.robindrew.common.date.timer;

import java.util.concurrent.TimeUnit;

import com.robindrew.common.date.duration.DurationNanos;
import com.robindrew.common.date.duration.IDuration;

/**
 * A Timer with nanosecond accuracy.
 */
public class NanoTimer extends Timer {

	/**
	 * Create and start a new timer.
	 * @return the timer.
	 */
	public static NanoTimer startTimer() {
		NanoTimer timer = new NanoTimer();
		timer.start();
		return timer;
	}

	@Override
	protected long getTime() {
		return System.nanoTime();
	}

	@Override
	public IDuration getDuration() {
		return new DurationNanos(elapsed());
	}

	@Override
	public TimeUnit getTimeUnit() {
		return TimeUnit.NANOSECONDS;
	}

}
