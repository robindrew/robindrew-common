package com.robindrew.common.date;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.TimeUnit;

public class UnitTime {

	public static UnitTime days(long number) {
		return new UnitTime(number, DAYS);
	}

	public static UnitTime hours(long number) {
		return new UnitTime(number, HOURS);
	}

	public static UnitTime minutes(long number) {
		return new UnitTime(number, MINUTES);
	}

	public static UnitTime seconds(long number) {
		return new UnitTime(number, SECONDS);
	}

	public static UnitTime millis(long number) {
		return new UnitTime(number, MILLISECONDS);
	}

	public static UnitTime micros(long number) {
		return new UnitTime(number, MICROSECONDS);
	}

	public static UnitTime nanos(long number) {
		return new UnitTime(number, NANOSECONDS);
	}

	/**
	 * Adds two unit times together, normalizing the units between them.
	 * @param unit1 the first unit.
	 * @param unit2 the second unit.
	 * @return the two units added, normalized to the smaller TimeUnit.
	 */
	public static UnitTime add(UnitTime unit1, UnitTime unit2) {

		// Reverse the add if the
		if (unit1.getUnit().ordinal() > unit2.getUnit().ordinal()) {
			return add(unit2, unit1);
		}

		long time = unit1.getTime();
		TimeUnit unit = unit1.getUnit();

		time += unit2.getTime(unit);
		return new UnitTime(time, unit);
	}

	/** The amount of time. */
	private final long time;
	/** The unit of time. */
	private final TimeUnit unit;

	/**
	 * Creates a new {@link UnitTime}.
	 * @param time the amount of time.
	 * @param unit the unit of time.
	 */
	public UnitTime(long time, TimeUnit unit) {
		if (time < 0) {
			throw new IllegalArgumentException("time=" + time);
		}
		if (unit == null) {
			throw new NullPointerException("unit");
		}
		this.time = time;
		this.unit = unit;
	}

	/**
	 * Returns the raw time value.
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Returns the time converted to the given {@link TimeUnit}.
	 * @param unit the {@link TimeUnit} to convert to.
	 * @return the converted time.
	 */
	public long getTime(TimeUnit unit) {
		return unit.convert(getTime(), getUnit());
	}

	public long toNanos() {
		return getTime(NANOSECONDS);
	}

	public long toMicros() {
		return getTime(MICROSECONDS);
	}

	public long toMillis() {
		return getTime(MILLISECONDS);
	}

	public long toSeconds() {
		return getTime(SECONDS);
	}

	public long toMinutes() {
		return getTime(MINUTES);
	}

	public long toHours() {
		return getTime(HOURS);
	}

	public long toDays() {
		return getTime(DAYS);
	}

	public long toWeeks() {
		return getTime(DAYS) * 7;
	}

	/**
	 * Returns the {@link TimeUnit}.
	 */
	public TimeUnit getUnit() {
		return unit;
	}

	@Override
	public int hashCode() {
		return ((int) time) * unit.hashCode() * 1999;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		UnitTime other = (UnitTime) object;
		if (time != other.time) {
			return false;
		}
		if (unit != other.unit) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return time + " " + unit;
	}
}
