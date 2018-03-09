package com.robindrew.common.date;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public interface IDateFormat {

	/**
	 * Returns the underlying pattern.
	 * @return the underlying pattern.
	 */
	String getPattern();

	/**
	 * Returns the underlying date format.
	 * @return the underlying date format.
	 */
	DateFormat getDateFormat();

	/**
	 * Format the given date.
	 * @param date the date.
	 * @return the formatted date.
	 */
	String format(Date date);

	/**
	 * Format the given date.
	 * @param date the date.
	 * @return the formatted date.
	 */
	String format(Calendar date);

	/**
	 * Format the given date.
	 * @param date the date in milliseconds.
	 * @return the formatted date.
	 */
	String format(long date);

	/**
	 * Parse the given date string.
	 * @param date the date string.
	 * @return the parsed date.
	 */
	Date parse(String date);

	/**
	 * Parse the given date string.
	 * @param date the date string.
	 * @return the parsed date.
	 */
	Date parseDate(String date);

	/**
	 * Parse the given date string.
	 * @param date the date string.
	 * @param calendar the calendar to parse in to.
	 * @return the parsed date.
	 */
	Calendar parse(String date, Calendar calendar);

	/**
	 * Parse the given date string.
	 * @param date the date string.
	 * @return the parsed date.
	 */
	Calendar parseCalendar(String date);

	/**
	 * Parse the given date string.
	 * @param date the date string.
	 * @return the parsed date.
	 */
	long parseTimeInMillis(String date);

	/**
	 * Remove the day postfixes (e.g. st, nd, th) from the given date string.
	 * @param date the date.
	 * @return the resulting date
	 */
	String removeDayPostfixes(String date);

}
