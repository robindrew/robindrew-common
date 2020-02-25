package com.robindrew.common.date.timer;

import java.util.concurrent.TimeUnit;

/**
 * A Duration Timer.
 */
public abstract class Timer implements ITimer {

	/** The start time. */
	private long startTime = 0;
	/** The stop time. */
	private long stopTime = 0;

	/**
	 * Returns the start time.
	 * @return the start time.
	 */
	@Override
	public long getStartTime() {
		return startTime;
	}

	/**
	 * Returns the stop time.
	 * @return the stop time.
	 */
	@Override
	public long getStopTime() {
		return stopTime;
	}

	/**
	 * Reset this timer.
	 */
	@Override
	public void reset() {
		startTime = 0;
		stopTime = 0;
	}

	/**
	 * Start this timer (resets if already running).
	 */
	@Override
	public ITimer start() {
		reset();
		this.startTime = getTime();
		return this;
	}

	/**
	 * Returns true if started.
	 * @return true if started.
	 */
	@Override
	public boolean isStarted() {
		return startTime > 0;
	}

	/**
	 * Stop! (must start a timer to stop it).
	 */
	@Override
	public ITimer stop() {
		// Record time first for best timing accuracy we could hope to achieve
		long time = getTime();
		// Multiple calls to stop should do nothing (to avoid confusing results)
		if (!isStopped()) {
			if (!isStarted()) {
				throw new IllegalStateException("Not Started");
			}
			this.stopTime = time;
		}
		return this;
	}

	/**
	 * Returns true if stopped.
	 * @return true if stopped.
	 */
	@Override
	public boolean isStopped() {
		return stopTime > 0;
	}

	/**
	 * Returns the time elapsed (from start to stop or current time if still running).
	 */
	@Override
	public long elapsed() {
		if (!isStarted()) {
			throw new IllegalStateException("Not Started");
		}
		if (!isStopped()) {
			return getTime() - startTime;
		}
		return stopTime - startTime;
	}

	/**
	 * Returns true if this has exceeded the given duration.
	 * @param duration the duration.
	 * @return true if this has exceeded the given duration.
	 */
	@Override
	public boolean hasExceeded(long duration) {
		if (duration < 0) {
			throw new IllegalArgumentException("duration=" + duration);
		}
		return elapsed() > duration;
	}

	/**
	 * Returns true if this has exceeded the given duration.
	 * @param duration the duration.
	 * @param unit the time unit.
	 * @return true if this has exceeded the given duration.
	 */
	@Override
	public boolean hasExceeded(long duration, TimeUnit unit) {
		long value = getTimeUnit().convert(duration, unit);
		return hasExceeded(value);
	}

	@Override
	public String toString() {
		if (!isStarted()) {
			return "Not Started";
		}
		return getDuration().toString();
	}

	@Override
	public long elapsed(TimeUnit unit) {
		return unit.convert(elapsed(), getTimeUnit());
	}

	/**
	 * Returns the time.
	 * @return the time.
	 */
	protected abstract long getTime();
}
