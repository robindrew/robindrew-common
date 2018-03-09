package com.robindrew.common.date.duration;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

import com.robindrew.common.date.UnitTime;

/**
 * A Duration.
 * @author Robin Drew
 */
public abstract class Duration implements IDuration {

	/** The decimal format. */
	private static final NumberFormat format = new DecimalFormat();

	/** The duration. */
	private final long duration;

	/**
	 * Creates a new duration.
	 * @param duration the duration.
	 */
	protected Duration(long duration) {
		this.duration = duration;
	}

	/**
	 * Creates a new duration.
	 * @param timestamp1 the first timestamp.
	 * @param timestamp2 the second timestamp.
	 */
	protected Duration(long timestamp1, long timestamp2) {
		if (timestamp1 > timestamp2) {
			this.duration = timestamp1 - timestamp2;
		} else {
			this.duration = timestamp2 - timestamp1;
		}
	}

	@Override
	public final long getDuration() {
		return duration;
	}

	@Override
	public final UnitTime toUnitTime() {
		return new UnitTime(getDuration(), getTimeUnit());
	}

	/**
	 * Returns the time.
	 * @return the time.
	 */
	public abstract long getInstant();

	/**
	 * Returns the time unit.
	 * @return the time unit.
	 */
	@Override
	public abstract TimeUnit getTimeUnit();

	/**
	 * Returns this time in nanoseconds.
	 * @return this time in nanoseconds.
	 */
	@Override
	public long getNanos() {
		return getTimeUnit().toNanos(duration);
	}

	/**
	 * Returns this time in milliseconds.
	 * @return this time in milliseconds.
	 */
	@Override
	public long getMillis() {
		return getTimeUnit().toMillis(duration);
	}

	/**
	 * Returns this time in seconds.
	 * @return this time in seconds.
	 */
	@Override
	public long getSeconds() {
		return getTimeUnit().toSeconds(duration);
	}

	@Override
	public long getDuration(TimeUnit unit) {
		return unit.convert(getDuration(), getTimeUnit());
	}

	@Override
	public String toString() {

		// Nanos
		long nanos = getNanos() % 1000000;
		long micros = getNanos() / 1000;
		if (micros <= 0) {
			synchronized (format) {
				return format.format(nanos) + " ns";
			}
		}

		// Microseconds
		long millis = micros / 1000;
		if (millis <= 0) {
			nanos %= 1000;
			nanos /= 1000;
			return micros + "." + nanos + " μs";
		}

		// Milliseconds
		long seconds = millis / 1000;
		if (seconds <= 0) {
			micros %= 1000;
			micros /= 1000;
			return millis + "." + micros + " ms";
		}

		// Seconds
		long minutes = seconds / 60;
		if (minutes <= 0) {
			millis %= 1000;
			millis /= 100;
			return seconds + "." + millis + " s";
		}

		// Minutes
		long hours = minutes / 60;
		if (hours <= 0) {
			seconds %= 60;
			seconds = (seconds * 10) / 60;
			return minutes + "." + seconds + " m";
		}

		// Hours
		long days = hours / 24;
		if (days <= 0) {
			minutes %= 60;
			minutes = (minutes * 10) / 60;
			return hours + "." + minutes + " h";
		}

		// Days
		hours %= 24;
		hours = (hours * 10) / 24;
		synchronized (format) {
			return format.format(days) + "." + hours + " d";
		}
	}

	@Override
	public int hashCode() {
		return (int) getNanos();
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object instanceof Duration) {
			Duration duration = (Duration) object;
			return getNanos() == duration.getNanos();
		}
		return false;
	}

	/**
	 * Compare this to the given duration.
	 * @param duration the duration.
	 * @return 0 if equal, 1 if greater, -1 if less.
	 */
	@Override
	public int compareTo(IDuration duration) {
		long nanos1 = this.getNanos();
		long nanos2 = duration.getNanos();
		if (nanos1 == nanos2) {
			return 0;
		}
		if (nanos1 > nanos2) {
			return 1;
		}
		return -1;
	}
}
