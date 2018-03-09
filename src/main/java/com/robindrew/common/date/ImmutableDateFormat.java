package com.robindrew.common.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A Thread-Safe Immutable Date Format.
 */
public class ImmutableDateFormat implements IDateFormat {

	public static final String DEFAULT_FORMAT = "yy-MM-dd HH:mm:ss,S";

	/** The date format cache. */
	private static final ConcurrentMap<String, IDateFormat> formatCache = new ConcurrentHashMap<String, IDateFormat>();

	/**
	 * Returns the date format for the given pattern.
	 * @param pattern the pattern.
	 * @return the date format.
	 */
	public static IDateFormat getInstance(String pattern) {
		IDateFormat format = formatCache.get(pattern);
		if (format == null) {
			pattern = pattern.intern();
			format = new ImmutableDateFormat(pattern);
			formatCache.put(format.getPattern(), format);
		}
		return format;
	}

	/**
	 * Returns the default date format.
	 * @return the default date format.
	 */
	public static IDateFormat getInstance() {
		return getInstance(DEFAULT_FORMAT);
	}

	/** The underlying date format. */
	private final SimpleDateFormat dateFormat;

	/**
	 * Creates a new concurrent date format.
	 * @param pattern the pattern.
	 */
	private ImmutableDateFormat(String pattern) {
		this.dateFormat = new SimpleDateFormat(pattern);
	}

	/**
	 * Returns the underlying pattern.
	 * @return the underlying pattern.
	 */
	@Override
	public String getPattern() {
		return dateFormat.toPattern();
	}

	/**
	 * Returns the date format (synchronize operations on it).
	 * @return the date format.
	 */
	@Override
	public DateFormat getDateFormat() {
		return dateFormat;
	}

	/**
	 * Format the given date.
	 * @param date the date.
	 * @return the formatted date.
	 */
	@Override
	public String format(Date date) {
		synchronized (dateFormat) {
			return dateFormat.format(date);
		}
	}

	/**
	 * Format the given date.
	 * @param date the date.
	 * @return the formatted date.
	 */
	@Override
	public String format(Calendar date) {
		return format(date.getTime());
	}

	/**
	 * Format the given date.
	 * @param date the date in milliseconds.
	 * @return the formatted date.
	 */
	@Override
	public String format(long date) {
		return format(new Date(date));
	}

	/**
	 * Parse the given date string.
	 * @param date the date string.
	 * @return the parsed date.
	 */
	@Override
	public Date parse(String date) {
		try {
			synchronized (dateFormat) {
				return dateFormat.parse(date);
			}
		} catch (ParseException e) {
			throw new IllegalArgumentException("date: '" + date + "'", e);
		}
	}

	/**
	 * Parse the given date string.
	 * @param date the date string.
	 * @return the parsed date.
	 */
	@Override
	public Date parseDate(String date) {
		return parse(date);
	}

	/**
	 * Parse the given date string.
	 * @param date the date string.
	 * @param calendar the calendar to parse in to.
	 * @return the parsed date.
	 */
	@Override
	public Calendar parse(String date, Calendar calendar) {
		calendar.setTime(parse(date));
		return calendar;
	}

	/**
	 * Parse the given date string.
	 * @param date the date string.
	 * @return the parsed date.
	 */
	@Override
	public Calendar parseCalendar(String date) {
		return parse(date, Calendar.getInstance());
	}

	/**
	 * Parse the given date string.
	 * @param date the date string.
	 * @return the parsed date.
	 */
	@Override
	public long parseTimeInMillis(String date) {
		return parse(date).getTime();
	}

	@Override
	public String removeDayPostfixes(String date) {
		boolean digit = false;
		for (int i = 0; i < date.length() - 1; i++) {
			char c = date.charAt(i);
			if (digit) {
				if (isDayPostfix(date, i, "st")) {
					date = date.substring(0, i) + date.substring(i + 2);
					return removeDayPostfixes(date);
				}
				if (isDayPostfix(date, i, "nd")) {
					date = date.substring(0, i) + date.substring(i + 2);
					return removeDayPostfixes(date);
				}
				if (isDayPostfix(date, i, "rd")) {
					date = date.substring(0, i) + date.substring(i + 2);
					return removeDayPostfixes(date);
				}
				if (isDayPostfix(date, i, "th")) {
					date = date.substring(0, i) + date.substring(i + 2);
					return removeDayPostfixes(date);
				}
			}
			digit = Character.isDigit(c);
		}
		return date;
	}

	private boolean isDayPostfix(String date, int index, String postfix) {
		if (date.charAt(index + 0) != postfix.charAt(0)) {
			return false;
		}
		if (date.charAt(index + 1) != postfix.charAt(1)) {
			return false;
		}
		return true;
	}
}
