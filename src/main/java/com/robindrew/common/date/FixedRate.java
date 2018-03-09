package com.robindrew.common.date;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.robindrew.common.text.Strings;

public class FixedRate {

	private long frequency = 0;
	private long offset = 0;

	public FixedRate() {
	}

	public FixedRate(long frequency) {
		this(new UnitTime(frequency, MILLISECONDS));
	}

	public FixedRate(UnitTime frequency) {
		setFrequency(frequency);
	}

	public final long getFrequency() {
		return frequency;
	}

	public final long getOffset() {
		return offset;
	}

	public final void setFrequency(UnitTime frequency) {
		if (frequency.getTime() < 1) {
			throw new IllegalArgumentException("frequency=" + frequency);
		}
		this.frequency = frequency.getTime(MILLISECONDS);
	}

	public final void setOffset(UnitTime offset) {
		if (offset.getTime() < 0) {
			throw new IllegalArgumentException("offset=" + offset);
		}
		this.offset = offset.getTime(MILLISECONDS);
	}

	public final void addOffset(UnitTime offset) {
		if (offset.getTime() < 0) {
			throw new IllegalArgumentException("offset=" + offset);
		}
		long newOffset = getOffset() + offset.getTime(MILLISECONDS);
		setOffset(new UnitTime(newOffset, MILLISECONDS));
	}

	/**
	 * To avoid two calls supplying the same time, supply the previous time.
	 * @param previousTime the result of the previous call to getNextTime()
	 */
	public final long getNextTime(long previousTime) {
		if (frequency < 1) {
			throw new IllegalStateException("frequency not set");
		}
		if (offset < 0) {
			throw new IllegalStateException("offset not set");
		}
		long timeNow = System.currentTimeMillis();
		long nextTime = timeNow;
		nextTime /= frequency;
		nextTime *= frequency;
		nextTime += offset;
		while (nextTime < timeNow || nextTime <= previousTime) {
			nextTime += frequency;
		}
		return nextTime;
	}

	public final long getNextTime() {
		return getNextTime(Long.MIN_VALUE);
	}

	public final long getSleepTime(long previousTime) {
		if (frequency < 1) {
			throw new IllegalStateException("frequency not set");
		}
		if (offset < 0) {
			throw new IllegalStateException("offset not set");
		}
		long timeNow = System.currentTimeMillis();
		long nextTime = timeNow;
		nextTime /= frequency;
		nextTime *= frequency;
		nextTime += offset;
		while (nextTime < timeNow || nextTime == previousTime) {
			nextTime += frequency;
		}
		return nextTime - timeNow;
	}

	public final long getSleepTime() {
		return getSleepTime(Long.MIN_VALUE);
	}

	@Override
	public String toString() {
		return Strings.toString(this);
	}
}
