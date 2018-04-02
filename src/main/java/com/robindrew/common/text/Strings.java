package com.robindrew.common.text;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.gson.GsonBuilder;
import com.robindrew.common.date.duration.DurationMillis;
import com.robindrew.common.lang.bytes.Bytes;
import com.robindrew.common.text.selection.Selection;
import com.robindrew.common.text.selection.SelectionOption;
import com.robindrew.common.util.Java;
import com.robindrew.common.util.Numbers;

/**
 * A utility that brings together common text processing functionality.
 */
public class Strings {

	/** The pattern cache. */
	private static final ConcurrentMap<String, Pattern> patternCache = new ConcurrentHashMap<String, Pattern>();

	/** The decimal format. */
	private static final NumberFormat format = new DecimalFormat();

	/**
	 * Utility class - private constructor.
	 */
	private Strings() {
	}

	public static String json(Object object) {
		return json(object, false);
	}

	public static String json(Object object, boolean formatted) {
		GsonBuilder builder = new GsonBuilder();
		if (formatted) {
			builder = builder.setPrettyPrinting();
		}
		return builder.create().toJson(object);
	}

	public static String number(Collection<?> collection) {
		return number(collection.size());
	}

	public static String number(byte[] bytes) {
		return number(bytes.length);
	}

	public static String number(Map<?, ?> map) {
		return number(map.size());
	}

	/**
	 * Format the given number.
	 * @param number the number.
	 * @return the formatted text.
	 */
	public static String number(Number number) {
		// Optimisation: NumberFormat is orders of magnitude slower!
		if (isWholeNumber(number)) {
			return number(number.longValue());
		}
		return NumberFormat.getInstance().format(number);
	}

	private static boolean isWholeNumber(Number number) {
		// We exclude BigInteger as it cannot have an accurate longValue()
		return number instanceof Integer || number instanceof Long || number instanceof Short || number instanceof Byte;
	}

	/**
	 * Format the given number.
	 * @param number the number.
	 * @return the formatted text.
	 */
	public static String number(long number) {
		// Optimisation: NumberFormat is orders of magnitude slower!
		if (number < 1000 && number > -1000) {
			return String.valueOf(number);
		}
		return NumberFormat.getInstance().format(number);
	}

	/**
	 * Returns a string representation of the given object.
	 * @param object the object.
	 * @return the string representation.
	 */
	public static String toString(Object object) {
		return ToStringBuilder.reflectionToString(object, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * Returns the given throwable as a stack trace.
	 * @param t the throwable.
	 * @return the stack trace.
	 */
	public static String getStackTrace(Throwable t) {
		return Throwables.getStackTraceAsString(t);
	}

	/**
	 * Returns an instance of the given pattern.
	 * @param regex the regular expression.
	 * @return the pattern.
	 */
	public static final Pattern getPattern(String regex) {
		Pattern pattern = patternCache.get(regex);
		if (pattern == null) {
			regex = regex.intern();
			pattern = Pattern.compile(regex);
			patternCache.put(regex, pattern);
		}
		return pattern;
	}

	/**
	 * Returns true if the given regular expression matches the text.
	 * @param regex the regular expression.
	 * @param text the text.
	 * @return true if the given regular expression matches the text.
	 */
	public static final boolean matches(String regex, String text) {
		Pattern pattern = getPattern(regex);
		return pattern.matcher(text).matches();
	}

	/**
	 * Returns the string value of the given object or the default if the value is null.
	 * @param object the object.
	 * @param nullDefault the default value to return if the object is null.
	 * @return the resulting string.
	 */
	public static final String valueOf(Object object, String nullDefault) {
		return Objects.toString(object, nullDefault);
	}

	/**
	 * Returns the index of the given needle in the haystack, ignoring case.
	 * @param haystack the haystack to search.
	 * @param needle the needle.
	 * @param index the start index.
	 * @return the index.
	 */
	public static int indexOfIgnoreCase(CharSequence haystack, String needle, int index) {
		if (needle.length() == 0) {
			return index;
		}
		if (needle.length() > haystack.length()) {
			return -1;
		}
		int subIndex = 0;
		for (int i = index; i < haystack.length(); i++) {
			char c1 = Character.toLowerCase(haystack.charAt(i));
			char c2 = Character.toLowerCase(needle.charAt(subIndex));
			if (c1 == c2) {
				subIndex++;
				if (subIndex == needle.length()) {
					return i - subIndex + 1;
				}
			} else {
				subIndex = 0;
			}
		}
		return -1;
	}

	/**
	 * Extract a portion of text from the given string.
	 * @param text the text to extract from.
	 */
	public static String extract(String text, String begin, String end) {
		return new Selection(text).extract(begin, end);
	}

	/**
	 * Extract a portion of text from the given string.
	 * @param text the text to extract from.
	 */
	public static String extract(String text, char begin, char end) {
		return extract(text, String.valueOf(begin), String.valueOf(end));
	}

	/**
	 * Extract a portion of text from the given string.
	 * @param text the text to extract from.
	 */
	public static String extract(String text, String begin, String end, SelectionOption... options) {
		return new Selection(text).extract(begin, end, options);
	}

	/**
	 * Extract a portion of text from the given string.
	 * @param text the text to extract from.
	 */
	public static String extract(String text, char begin, char end, SelectionOption... options) {
		return extract(text, String.valueOf(begin), String.valueOf(end), options);
	}

	/**
	 * Format the given bytes in the array.
	 * @param array the array of bytes.
	 * @return the formatted text.
	 */
	public static String bytes(byte[] array) {
		return bytes(array.length);
	}

	/**
	 * Format the given bytes.
	 * @param amount the bytes.
	 * @return the formatted text.
	 */
	public static String bytes(long amount) {
		return bytes(amount, Bytes.BINARY_DIVISOR);
	}

	/**
	 * Format the given bytes.
	 * @param amount the bytes.
	 * @param divisor the divisor (1024 or 1000).
	 * @return the formatted text.
	 */
	public static String bytes(long amount, int divisor) {
		if (amount < 0) {
			throw new IllegalArgumentException("amount=" + amount);
		}
		if (divisor != Bytes.BINARY_DIVISOR && divisor != Bytes.DECIMAL_DIVISOR) {
			throw new IllegalArgumentException("divisor=" + divisor);
		}

		// Bytes
		long bytes = amount % divisor;
		long kb = amount / divisor;
		if (kb <= 0) {
			return bytes + " bytes";
		}

		// Kilobytes
		long mb = kb / divisor;
		if (mb <= 0) {
			bytes /= 100;
			return kb + "." + bytes + " KB";
		}

		// Megabytes
		long gb = mb / divisor;
		if (gb <= 0) {
			kb %= divisor;
			kb /= 100;
			return mb + "." + kb + " MB";
		}

		// Gigabytes
		long tb = gb / divisor;
		if (tb <= 0) {
			mb %= divisor;
			mb /= 100;
			return gb + "." + mb + " GB";
		}

		// Terabytes
		long pb = tb / divisor;
		if (pb <= 0) {
			gb %= divisor;
			gb /= 100;
			return tb + "." + gb + " TB";
		}

		// Petabytes
		tb %= divisor;
		tb /= 100;
		return pb + "." + tb + " PB";
	}

	/**
	 * Returns a percentage formatted from the given dividend and divisor.
	 * @param dividend the dividend (value).
	 * @param divisor the divisor (is percentage of)
	 * @return the formatted string.
	 */
	public static String percent(long dividend, long divisor) {

		// Shortcuts
		if (dividend == divisor) {
			return "100%";
		}
		if (dividend == 0) {
			return "0%";
		}
		if (divisor == 100) {
			return dividend + "%";
		}

		if (divisor > dividend && divisor % dividend == 0) {
			return percent(dividend, divisor, 0);
		}
		if (dividend > divisor && dividend % divisor == 0) {
			return percent(dividend, divisor, 0);
		}

		int precision = Numbers.countDigits(divisor / dividend);
		return percent(dividend, divisor, precision);
	}

	/**
	 * Returns a percentage formatted from the given dividend and divisor.
	 * @param dividend the dividend (value).
	 * @param divisor the divisor (is percentage of)
	 * @param precision the precision of the percentage.
	 * @return the formatted string.
	 */
	public static String percent(long dividend, long divisor, int precision) {
		if (dividend < 0) {
			throw new IllegalArgumentException("dividend=" + dividend);
		}
		if (divisor < 1) {
			throw new IllegalArgumentException("divisor=" + divisor);
		}
		if (precision < 0) {
			throw new IllegalArgumentException("precision=" + precision);
		}

		// Shortcuts
		if (precision == 0) {
			if (dividend == divisor) {
				return "100%";
			}
			if (dividend == 0) {
				return "0%";
			}
			if (divisor == 100) {
				return dividend + "%";
			}
		}

		long multiplier = getMultiplier(precision);
		long percent = (dividend * 100 * multiplier) / divisor;

		if (precision == 0) {
			return percent + "%";
		}
		long whole = percent / multiplier;
		long decimal = percent % multiplier;

		StringBuilder text = new StringBuilder();
		text.append(whole).append('.');
		int digits = Numbers.countDigits(decimal);
		while (digits < precision) {
			digits++;
			text.append('0');
		}
		text.append(decimal).append('%');
		return text.toString();
	}

	private static int getMultiplier(int precision) {
		int multiplier = 1;
		for (int i = 0; i < precision; i++) {
			multiplier *= 10;
		}
		return multiplier;
	}

	public static String date(Date date, String datePattern) {
		return date(date.getTime(), datePattern);
	}

	public static String date(long dateInMillis, String datePattern) {
		return new SimpleDateFormat(datePattern).format(dateInMillis);
	}

	public static String time(long timeInMillis) {
		long millis = timeInMillis;

		// Milliseconds
		long seconds = millis / 1000;
		if (seconds <= 0) {
			return millis + " millis";
		}

		// Seconds
		long minutes = seconds / 60;
		if (minutes <= 0) {
			millis %= 1000;
			millis /= 100;
			return seconds + "." + millis + " seconds";
		}

		// Minutes
		long hours = minutes / 60;
		if (hours <= 0) {
			seconds %= 60;
			seconds = (seconds * 10) / 60;
			return minutes + "." + seconds + " minutes";
		}

		// Hours
		long days = hours / 24;
		if (days <= 0) {
			minutes %= 60;
			minutes = (minutes * 10) / 60;
			return hours + "." + minutes + " hours";
		}

		// Days
		hours %= 24;
		hours = (hours * 10) / 24;
		synchronized (format) {
			return format.format(days) + "." + hours + " days";
		}
	}

	public static String toCamelCase(String name) {
		boolean upper = true;
		StringBuilder camel = new StringBuilder();
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (!isLetter(c) && !isDigit(c)) {
				upper = true;
				continue;
			}
			if (upper) {
				c = toUpperCase(c);
				upper = false;
			} else {
				c = toLowerCase(c);
			}
			camel.append(c);
		}
		return camel.toString();
	}

	public static String toConstantCase(String name) {
		boolean upper = true;
		StringBuilder constant = new StringBuilder();
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (isUpperCase(c)) {
				if (!upper) {
					upper = true;
					constant.append('_');
				}
				constant.append(c);
			} else {
				upper = false;
				constant.append(toUpperCase(c));
			}
		}
		return constant.toString();
	}

	public static String removePrefix(String text, String prefix) {
		if (!text.startsWith(prefix)) {
			throw new IllegalArgumentException("text: '" + text + "' does not start with prefix: '" + prefix + "'");
		}
		return text.substring(prefix.length());
	}

	public static String removeSuffix(String text, String suffix) {
		if (!text.endsWith(suffix)) {
			throw new IllegalArgumentException("text: '" + text + "' does not end with prefix: '" + suffix + "'");
		}
		return text.substring(0, text.length() - suffix.length());
	}

	public static String urlEncode(String text) {
		try {
			return URLEncoder.encode(text, Charsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw Java.propagate(e);
		}
	}

	public static String substring(String text, String from, String to) {
		return new Selection(text).extract(from, to);
	}

	public static String duration(long fromTimeMillis, long toTimeMillis) {
		return new DurationMillis(fromTimeMillis, toTimeMillis).toString();
	}

}
