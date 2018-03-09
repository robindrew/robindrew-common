package com.robindrew.common.util;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import com.robindrew.common.text.Strings;

/**
 * An Argument Checker Utility.
 */
public final class Check {

	/**
	 * Checks the named variable is not null.
	 * @param name the name.
	 * @param value the value.
	 * @return the value.
	 */
	public static final <T> T notNull(String name, T value) {
		if (value == null) {
			throw new NullPointerException("'" + name + "' is null");
		}
		return value;
	}

	/**
	 * Checks the named variable is null.
	 * @param name the name.
	 * @param value the value.
	 * @return the value.
	 */
	public static final <T> T isNull(String name, T value) {
		if (value != null) {
			throw new NullPointerException("'" + name + "' is not null");
		}
		return value;
	}

	/**
	 * Checks the named string Variable is not empty.
	 * @param name the name.
	 * @param value the string value.
	 * @return the string value.
	 */
	public static final String notEmpty(String name, String value) {
		notNull(name, value);
		if (value.length() == 0) {
			throw new IllegalArgumentException("'" + name + "' is empty");
		}
		return value;
	}

	public static final String notStartsWith(String name, String value, String startsWith) {
		notNull(name, value);
		if (!value.startsWith(startsWith)) {
			throw new IllegalArgumentException("'" + name + "' does not start with '" + startsWith + "'");
		}
		return value;
	}

	/**
	 * Checks the named primitive array Variable is not empty.
	 * @param name the name.
	 * @param array the array.
	 * @return the array.
	 */
	public static final <O> O notEmpty(String name, O array) {
		notNull(name, array);
		if (Array.getLength(array) == 0) {
			throw new IllegalArgumentException("'" + name + "' is empty");
		}
		return array;
	}

	/**
	 * Checks the named array Variable is not empty.
	 * @param name the name.
	 * @param array the array.
	 * @return the array.
	 */
	public static final <O> O[] notEmpty(String name, O[] array) {
		notNull(name, array);
		if (array.length == 0) {
			throw new IllegalArgumentException("'" + name + "' is empty");
		}
		return array;
	}

	/**
	 * Checks the named collection Variable is not empty.
	 * @param name the name.
	 * @param collection the collection.
	 * @return the collection.
	 */
	public static final <T, C extends Collection<T>> C notEmpty(String name, C collection) {
		notNull(name, collection);
		if (collection.isEmpty()) {
			throw new IllegalArgumentException("'" + name + "' is empty");
		}
		return collection;
	}

	/**
	 * Checks the named map Variable is not empty.
	 * @param name the name.
	 * @param map the map.
	 * @return the map.
	 */
	public static final <K, V> Map<K, V> notEmpty(String name, Map<K, V> map) {
		notNull(name, map);
		if (map.isEmpty()) {
			throw new IllegalArgumentException("'" + name + "' is empty");
		}
		return map;
	}

	/**
	 * Checks the named collection Variable is not empty and elements are not null.
	 * @param name the name.
	 * @param collection the collection.
	 * @return the collection.
	 */
	public static final <T> Collection<T> notEmptyAndElementsNotNull(String name, Collection<T> collection) {
		notNull(name, collection);
		if (collection.isEmpty()) {
			throw new IllegalArgumentException("'" + name + "' is empty");
		}
		int index = 0;
		for (Object element : collection) {
			if (element == null) {
				throw new NullPointerException("'" + name + "', index=" + index + ", is null");
			}
			index++;
		}
		return collection;
	}

	/**
	 * Checks the named array Variable is not empty and elements are not null.
	 * @param name the name.
	 * @param array the array.
	 * @return the array.
	 */
	public static final <O> O[] notEmptyAndElementsNotNull(String name, O[] array) {
		if (array.length == 0) {
			throw new IllegalArgumentException("'" + name + "' is empty");
		}
		int index = 0;
		for (Object element : array) {
			if (element == null) {
				throw new NullPointerException("'" + name + "', index=" + index + ", is null");
			}
			index++;
		}
		return array;
	}

	public static final Collection<String> notEmptyAndElementsNotEmpty(String name, Collection<String> collection) {
		notNull(name, collection);
		if (collection.isEmpty()) {
			throw new IllegalArgumentException("'" + name + "' is empty");
		}
		int index = 0;
		for (String element : collection) {
			if (element == null) {
				throw new NullPointerException("'" + name + "', index=" + index + ", is null");
			}
			if (element.isEmpty()) {
				throw new IllegalArgumentException("'" + name + "', index=" + index + ", is empty: " + collection);
			}
			index++;
		}
		return collection;
	}

	/**
	 * Checks the named string matches the regular expression.
	 * @param name the name.
	 * @param value the string value.
	 * @param regex the regular expression.
	 * @return the value.
	 */
	public static final String matches(String name, String value, String regex) {
		Pattern pattern = Strings.getPattern(regex);
		matches(name, value, pattern);
		return value;

	}

	/**
	 * Checks the named string matches the pattern.
	 * @param name the name.
	 * @param value the string value.
	 * @param pattern the pattern.
	 * @return the value.
	 */
	public static final String matches(String name, String value, Pattern pattern) {
		notNull(name, value);
		if (!pattern.matcher(value).matches()) {
			throw new IllegalArgumentException("'" + name + "', value: '" + value + "' does not match pattern: '" + pattern.pattern() + "'");
		}
		return value;
	}

	/**
	 * Checks the value is within the given range.
	 * @param name the name.
	 * @param value the value.
	 * @param min the minimum valid value.
	 * @param max the minimum valid value.
	 * @return the value.
	 */
	public static final int range(String name, int value, int min, int max) {
		min(name, value, min);
		max(name, value, min);
		return value;
	}

	/**
	 * Checks the value is greater than or equal to the the given minimum.
	 * @param name the name.
	 * @param value the value.
	 * @param min the minimum valid value.
	 * @return the value.
	 */
	public static final int min(String name, int value, int min) {
		if (value < min) {
			throw new IllegalArgumentException("'" + name + "' is less than " + min);
		}
		return value;
	}

	/**
	 * Checks the value is less than or equal to the the given maximum.
	 * @param name the name.
	 * @param value the value.
	 * @param max the maximum valid value.
	 * @return the value.
	 */
	public static final int max(String name, int value, int max) {
		if (value > max) {
			throw new IllegalArgumentException("'" + name + "' is greater than " + max);
		}
		return value;
	}

	/**
	 * Checks the value is within the given range.
	 * @param name the name.
	 * @param value the value.
	 * @param min the minimum valid value.
	 * @param max the minimum valid value.
	 * @return the value.
	 */
	public static final long range(String name, long value, long min, long max) {
		min(name, value, min);
		max(name, value, min);
		return value;
	}

	/**
	 * Checks the value is greater than or equal to the the given minimum.
	 * @param name the name.
	 * @param value the value.
	 * @param min the minimum valid value.
	 * @return the value.
	 */
	public static final long min(String name, long value, long min) {
		if (value < min) {
			throw new IllegalArgumentException("'" + name + "' is less than " + min);
		}
		return value;
	}

	/**
	 * Checks the value is less than or equal to the the given maximum.
	 * @param name the name.
	 * @param value the value.
	 * @param max the maximum valid value.
	 * @return the value.
	 */
	public static final long max(String name, long value, long max) {
		if (value > max) {
			throw new IllegalArgumentException("'" + name + "' is greater than " + max);
		}
		return value;
	}

	/**
	 * Checks the value is within the given range.
	 * @param name the name.
	 * @param value the value.
	 * @param min the minimum valid value.
	 * @param max the minimum valid value.
	 * @return the value.
	 */
	public static final double range(String name, double value, double min, double max) {
		min(name, value, min);
		max(name, value, min);
		return value;
	}

	/**
	 * Checks the value is greater than or equal to the the given minimum.
	 * @param name the name.
	 * @param value the value.
	 * @param min the minimum valid value.
	 * @return the value.
	 */
	public static final double min(String name, double value, double min) {
		if (value < min) {
			throw new IllegalArgumentException("'" + name + "' is less than " + min);
		}
		if (Double.isInfinite(value)) {
			throw new IllegalArgumentException("'" + name + "' is infinite");
		}
		if (Double.isNaN(value)) {
			throw new IllegalArgumentException("'" + name + "' is not a number");
		}
		return value;
	}

	/**
	 * Checks the value is less than or equal to the the given maximum.
	 * @param name the name.
	 * @param value the value.
	 * @param max the maximum valid value.
	 * @return the value.
	 */
	public static final double max(String name, double value, double max) {
		if (value > max) {
			throw new IllegalArgumentException("'" + name + "' is greater than " + max);
		}
		if (Double.isInfinite(value)) {
			throw new IllegalArgumentException("'" + name + "' is infinite");
		}
		if (Double.isNaN(value)) {
			throw new IllegalArgumentException("'" + name + "' is not a number");
		}
		return value;
	}

	/**
	 * Checks the value is within the given range.
	 * @param name the name.
	 * @param value the value.
	 * @param min the minimum valid value.
	 * @param max the minimum valid value.
	 * @return the value.
	 */
	public static final float range(String name, float value, float min, float max) {
		min(name, value, min);
		max(name, value, min);
		return value;
	}

	/**
	 * Checks the value is greater than or equal to the the given minimum.
	 * @param name the name.
	 * @param value the value.
	 * @param min the minimum valid value.
	 * @return the value.
	 */
	public static final float min(String name, float value, float min) {
		if (value < min) {
			throw new IllegalArgumentException("'" + name + "' is less than " + min);
		}
		if (Float.isInfinite(value)) {
			throw new IllegalArgumentException("'" + name + "' is infinite");
		}
		if (Float.isNaN(value)) {
			throw new IllegalArgumentException("'" + name + "' is not a number");
		}
		return value;
	}

	/**
	 * Checks the value is less than or equal to the the given maximum.
	 * @param name the name.
	 * @param value the value.
	 * @param max the maximum valid value.
	 * @return the value.
	 */
	public static final float max(String name, float value, float max) {
		if (value > max) {
			throw new IllegalArgumentException("'" + name + "' is greater than " + max);
		}
		if (Float.isInfinite(value)) {
			throw new IllegalArgumentException("'" + name + "' is infinite");
		}
		if (Float.isNaN(value)) {
			throw new IllegalArgumentException("'" + name + "' is not a number");
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	public static <V> V notIn(String name, V value, V... notValues) {
		notNull(name, value);
		for (V notValue : notValues) {
			if (value.equals(notValue)) {
				throw new IllegalArgumentException("'" + name + "' can not have value " + notValue);
			}
		}
		return value;
	}

	public static String minLength(String name, String value, int minLength) {
		notNull(name, value);
		if (value.length() < minLength) {
			throw new IllegalArgumentException("'" + name + "' is shorter than " + minLength);
		}
		return value;
	}

	public static File exists(String name, File value) {
		notNull(name, value);
		if (!value.exists()) {
			throw new IllegalArgumentException("'" + name + "' does not exist: '" + value.getAbsolutePath() + "'");
		}
		return value;
	}

	public static File directory(String name, File value) {
		notNull(name, value);
		if (!value.isDirectory()) {
			throw new IllegalArgumentException("'" + name + "' is not a directory: '" + value + "'");
		}
		return value;
	}

	public static File file(String name, File value) {
		notNull(name, value);
		if (!value.isFile()) {
			throw new IllegalArgumentException("'" + name + "' is not a regular file: '" + value + "'");
		}
		return value;
	}

	public static File directory(String name, String value) {
		return directory(name, new File(value));
	}

	public static File file(String name, String value) {
		return file(name, new File(value));
	}

	public static File existsDirectory(String name, File value) {
		exists(name, value);
		directory(name, value);
		return value;
	}

	public static File existsFile(String name, File value) {
		exists(name, value);
		file(name, value);
		return value;
	}

	public static File existsDirectory(String name, String value) {
		return existsDirectory(name, new File(value));
	}

	public static File existsFile(String name, String value) {
		return existsFile(name, new File(value));
	}

	/**
	 * Inaccessible constructor.
	 */
	private Check() {
	}

}
