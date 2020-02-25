package com.robindrew.common.date.timer;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.robindrew.common.date.duration.DurationMillis;
import com.robindrew.common.date.duration.IDuration;

/**
 * A Timer with millisecond accuracy.
 */
public class MilliTimer extends Timer {

	/**
	 * Create and start a new timer.
	 * @return the timer.
	 */
	public static MilliTimer startTimer() {
		MilliTimer timer = new MilliTimer();
		timer.start();
		return timer;
	}

	/**
	 * Returns the date on which this timer started.
	 * @return the date on which this timer started.
	 */
	public Date getStartDate() {
		return new Date(getStartTime());
	}

	/**
	 * Returns the date on which this timer stopped.
	 * @return the date on which this timer stopped.
	 */
	public Date getStopDate() {
		return new Date(getStopTime());
	}

	@Override
	protected long getTime() {
		return System.currentTimeMillis();
	}

	@Override
	public IDuration getDuration() {
		return new DurationMillis(elapsed());
	}

	@Override
	public TimeUnit getTimeUnit() {
		return TimeUnit.MILLISECONDS;
	}

}
