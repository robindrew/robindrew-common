package com.robindrew.common.date;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Thread-safe, efficient date parsing &amp; formatting.
 * <p>
 * Performance tests reveal a significant drop in object allocation, speed and memory footprint (~5x).
 */
public final class Dates {

	/** The GMT Time Zone. */
	public static final TimeZone UTC = TimeZone.getTimeZone("UTC");
	/** The UTC Zone Id. */
	public static final ZoneId UTC_ZONE = ZoneOffset.UTC.normalized();

	/** The zero {@link LocalTime} - 0 hours, 0 minutes, 0 seconds, 0 nanos! */
	public static final LocalTime ZERO_LOCAL_TIME = LocalTime.of(0, 0, 0, 0);

	/** The number of nanoseconds per microsecond. */
	public static final long NANOS_PER_MICRO = 1000;
	/** The number of nanoseconds per millisecond. */
	public static final long NANOS_PER_MILLI = NANOS_PER_MICRO * 1000;
	/** The number of milliseconds in a second. */
	public static final long NANOS_PER_SECOND = NANOS_PER_MILLI * 1000;
	/** The number of milliseconds in a second. */
	public static final long MICROS_PER_SECOND = 1000000;
	/** The number of milliseconds in a second. */
	public static final long MILLIS_PER_SECOND = 1000;

	/**
	 * Inaccessible Constructor.
	 */
	private Dates() {
	}

	/**
	 * Converts a {@link LocalDateTime} from one timezone to another.
	 * @param date the date to convert.
	 * @param fromZone the timezone to convert from.
	 * @param toZone the timezone to convert to.
	 * @return the converted date.
	 */
	public static LocalDateTime convertDateTime(LocalDateTime date, ZoneId fromZone, ZoneId toZone) {
		ZonedDateTime local = date.atZone(fromZone);
		ZonedDateTime utc = ZonedDateTime.ofInstant(local.toInstant(), toZone);
		return utc.toLocalDateTime();
	}

	/**
	 * Parses the given date.
	 * @param pattern the date format pattern.
	 * @param date the date as a string.
	 * @return the date.
	 */
	public static final Date parseDate(String pattern, String date) {
		IDateFormat format = ImmutableDateFormat.getInstance(pattern);
		synchronized (format) {
			return format.parse(date);
		}
	}

	/**
	 * Parses the given date.
	 * @param pattern the date format pattern.
	 * @param date the date as a string.
	 * @return the date in milliseconds.
	 */
	public static final long parseMillis(String pattern, String date) {
		return parseDate(pattern, date).getTime();
	}

	/**
	 * Formats the given date.
	 * @param pattern the date format pattern.
	 * @param date the date.
	 * @return the date as a string.
	 */
	public static final String formatDate(String pattern, Date date) {
		IDateFormat format = ImmutableDateFormat.getInstance(pattern);
		return format.format(date);
	}

	/**
	 * Formats the given date.
	 * @param pattern the date format pattern.
	 * @param date the date.
	 * @return the date as a string.
	 */
	public static final String formatDate(String pattern, long date) {
		IDateFormat format = ImmutableDateFormat.getInstance(pattern);
		return format.format(date);
	}

	public static String formatDate(String pattern, LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return date.format(formatter);
	}

	public static String formatTime(String pattern, LocalTime time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return time.format(formatter);
	}

	/**
	 * Formats the given date in milliseconds.
	 * @param pattern the date format pattern.
	 * @param date the date in milliseconds.
	 * @return the date as a string.
	 */
	public static final String formatMillis(String pattern, long date) {
		return formatDate(pattern, new Date(date));
	}

	/**
	 * Returns the given seconds as milliseconds.
	 * @param seconds the seconds.
	 * @return the seconds.
	 */
	public static final long toMillis(int seconds) {
		return seconds * MILLIS_PER_SECOND;
	}

	/**
	 * Returns the given milliseconds as seconds.
	 * @param millis the milliseconds.
	 * @return the seconds.
	 */
	public static final int toSeconds(long millis) {
		return (int) (millis / MILLIS_PER_SECOND);
	}

	/**
	 * Returns the current time in seconds.
	 * @return the current time in seconds.
	 */
	public static final int currentTimeSeconds() {
		return (int) (System.currentTimeMillis() / MILLIS_PER_SECOND);
	}

	/**
	 * Returns the current time in milliseconds.
	 * @return the current time in milliseconds.
	 */
	public static final long currentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * Returns the given date as an integer date.
	 * @param year the year.
	 * @param month the month.
	 * @param day the day.
	 * @return the integer date.
	 */
	public static int toYYYYMMDD(int year, int month, int day) {
		return (year * 10000) + (month * 100) + day;
	}

	/**
	 * Returns the given date as an integer date.
	 * @param date the date.
	 * @return the integer date.
	 */
	public static int toYYYYMMDD(LocalDate date) {
		int year = date.getYear();
		int month = date.getMonthValue();
		int day = date.getDayOfMonth();
		return toYYYYMMDD(year, month, day);
	}

	/**
	 * Returns the given nanoseconds as milliseconds.
	 * @param nanoseconds the nanoseconds.
	 * @return the milliseconds.
	 */
	public static long nanosToMillis(long nanoseconds) {
		return nanoseconds / NANOS_PER_MILLI;
	}

	public static String toString(long date) {
		return formatDate(ImmutableDateFormat.DEFAULT_FORMAT, date);
	}

	public static String toString(Date date) {
		return toString(date.getTime());
	}

	public static boolean isWeekend(DayOfWeek day) {
		return day.equals(DayOfWeek.SATURDAY) || day.equals(DayOfWeek.SUNDAY);
	}

	public static boolean isWeekday(DayOfWeek day) {
		return !isWeekend(day);
	}

	public static boolean isWeekend(LocalDateTime date) {
		return isWeekend(date.getDayOfWeek());
	}

	public static boolean isWeekday(LocalDateTime date) {
		return isWeekday(date.getDayOfWeek());
	}

	public static long toMillis(LocalDateTime date) {
		return toMillis(date, ZoneOffset.UTC);
	}

	public static long toMillis(LocalDateTime date, ZoneOffset offset) {
		return date.toInstant(offset).toEpochMilli();
	}

	public static LocalDateTime toLocalDateTime(long millis) {
		Instant instant = Instant.ofEpochMilli(millis);
		return LocalDateTime.ofInstant(instant, UTC_ZONE);
	}

	public static ChronoUnit toChronoUnit(TimeUnit unit) {
		switch (unit) {
			case NANOSECONDS:
				return ChronoUnit.NANOS;
			case MICROSECONDS:
				return ChronoUnit.MICROS;
			case MILLISECONDS:
				return ChronoUnit.MILLIS;
			case SECONDS:
				return ChronoUnit.SECONDS;
			case MINUTES:
				return ChronoUnit.MINUTES;
			case HOURS:
				return ChronoUnit.HOURS;
			case DAYS:
				return ChronoUnit.DAYS;
			default:
				throw new IllegalStateException("Unable to convert to ChronoUnit: " + unit);
		}
	}

	public static UnitTime toUnitTime(ChronoUnit unit) {
		switch (unit) {
			case NANOS:
				return new UnitTime(1, TimeUnit.NANOSECONDS);
			case MICROS:
				return new UnitTime(1, TimeUnit.MICROSECONDS);
			case MILLIS:
				return new UnitTime(1, TimeUnit.MILLISECONDS);
			case SECONDS:
				return new UnitTime(1, TimeUnit.SECONDS);
			case MINUTES:
				return new UnitTime(1, TimeUnit.MINUTES);
			case HOURS:
				return new UnitTime(1, TimeUnit.HOURS);
			case HALF_DAYS:
				return new UnitTime(12, TimeUnit.HOURS);
			case DAYS:
				return new UnitTime(1, TimeUnit.DAYS);
			case WEEKS:
				return new UnitTime(7, TimeUnit.DAYS);

			// Estimated from here on
			case MONTHS:
				return new UnitTime(30, TimeUnit.DAYS);
			case YEARS:
				return new UnitTime(365, TimeUnit.DAYS);
			case DECADES:
				return new UnitTime(3650, TimeUnit.DAYS);
			case CENTURIES:
				return new UnitTime(36500, TimeUnit.DAYS);
			case MILLENNIA:
				return new UnitTime(365000, TimeUnit.DAYS);
			case ERAS:
			case FOREVER:
			default:
				throw new IllegalStateException("Not supported: " + unit);
		}
	}

	public static long getDurationMillis(TimeUnit unit) {
		return unit.toMillis(1);
	}

	public static LocalDateTime toLocalDateTime(LocalDate date) {
		return LocalDateTime.of(date, ZERO_LOCAL_TIME);
	}

}
