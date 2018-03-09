package com.robindrew.common.date;

import static com.robindrew.common.date.Dates.MICROS_PER_SECOND;
import static com.robindrew.common.date.Dates.MILLIS_PER_SECOND;
import static com.robindrew.common.date.Dates.NANOS_PER_MICRO;
import static com.robindrew.common.date.Dates.NANOS_PER_MILLI;
import static com.robindrew.common.date.Dates.NANOS_PER_SECOND;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MICROS;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.NANOS;
import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class UnitChrono {

	public static UnitChrono days(long number) {
		return new UnitChrono(number, DAYS);
	}

	public static UnitChrono hours(long number) {
		return new UnitChrono(number, HOURS);
	}

	public static UnitChrono minutes(long number) {
		return new UnitChrono(number, MINUTES);
	}

	public static UnitChrono seconds(long number) {
		return new UnitChrono(number, SECONDS);
	}

	public static UnitChrono millis(long number) {
		return new UnitChrono(number, MILLIS);
	}

	public static UnitChrono micros(long number) {
		return new UnitChrono(number, MICROS);
	}

	public static UnitChrono nanos(long number) {
		return new UnitChrono(number, NANOS);
	}

	/**
	 * Adds two unit times together, normalizing the units between them.
	 * @param unit1 the first unit.
	 * @param unit2 the second unit.
	 * @return the two units added, normalized to the smaller ChronoUnit.
	 */
	public static UnitChrono add(UnitChrono unit1, UnitChrono unit2) {

		// Reverse the add if the
		if (unit1.getUnit().ordinal() > unit2.getUnit().ordinal()) {
			return add(unit2, unit1);
		}

		long time = unit1.getTime();
		ChronoUnit unit = unit1.getUnit();

		time += unit2.getTime(unit);
		return new UnitChrono(time, unit);
	}

	/** The amount of time. */
	private final long time;
	/** The unit of time. */
	private final ChronoUnit unit;

	/**
	 * Creates a new {@link UnitChrono}.
	 * @param time the amount of time.
	 * @param unit the unit of time.
	 */
	public UnitChrono(long time, ChronoUnit unit) {
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

	private Duration getDuration() {
		return unit.getDuration();
	}

	/**
	 * Returns the time converted to the given {@link ChronoUnit}.
	 * @param unit the {@link ChronoUnit} to convert to.
	 * @return the converted time.
	 */
	public long getTime(ChronoUnit unit) {
		switch (unit) {
			case MILLIS:
				return toMillis();
			case SECONDS:
				return toSeconds();
			case MINUTES:
				return toMinutes();
			case HOURS:
				return toHours();
			case HALF_DAYS:
				return toDays() / 2;
			case DAYS:
				return toDays();
			case WEEKS:
				return toWeeks();

			// Estimated from here on
			case MONTHS:
				return toMonths();
			case YEARS:
				return toYears();
			case DECADES:
				return toDecades();
			case CENTURIES:
				return toCenturies();
			case MILLENNIA:
				return toMillennia();

			// We don't support these yet
			case NANOS:
			case MICROS:
			case ERAS:
			case FOREVER:
			default:
				throw new IllegalStateException("Not supported: " + unit);
		}
	}

	public long toNanos() {
		long nanos = getDuration().getNano();
		long seconds = getDuration().getSeconds();
		return (nanos + (seconds * NANOS_PER_SECOND)) * time;
	}

	public long toMicros() {
		long nanos = getDuration().getNano();
		long seconds = getDuration().getSeconds();
		return ((nanos / NANOS_PER_MICRO) + (seconds / MICROS_PER_SECOND)) * time;
	}

	public long toMillis() {
		long nanos = getDuration().getNano();
		long seconds = getDuration().getSeconds();
		return ((nanos / NANOS_PER_MILLI) + (seconds * MILLIS_PER_SECOND)) * time;
	}

	public long toSeconds() {
		return getDuration().getSeconds() * time;
	}

	public long toMinutes() {
		return toSeconds() / (60);
	}

	public long toHours() {
		return toSeconds() / (60 * 60);
	}

	public long toDays() {
		return toSeconds() / (60 * 60 * 24);
	}

	public long toWeeks() {
		return toSeconds() / (60 * 60 * 24 * 7);
	}

	public long toMonths() {
		return toSeconds() / (60 * 60 * 24 * 365 / 12);
	}

	public long toYears() {
		return toSeconds() / (60 * 60 * 24 * 365);
	}

	public long toDecades() {
		return toSeconds() / (60 * 60 * 24 * 365 * 10);
	}

	public long toCenturies() {
		return toSeconds() / (60 * 60 * 24 * 365 * 100);
	}

	public long toMillennia() {
		return toSeconds() / (60 * 60 * 24 * 365 * 1000);
	}

	/**
	 * Returns the {@link ChronoUnit}.
	 */
	public ChronoUnit getUnit() {
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
		UnitChrono other = (UnitChrono) object;
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
