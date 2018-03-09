package com.robindrew.common.util;

public class Numbers {

	public static int parseFirstInt(String text) {
		text = parseDigits(text);
		return Integer.parseInt(text);
	}

	public static long parseFirstLong(String text) {
		text = parseDigits(text);
		return Long.parseLong(text);
	}

	private static String parseDigits(String text) {
		int start = -1;
		int end = text.length();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (start == -1) {
				if (Character.isDigit(c)) {
					start = i;
					continue;
				}
			} else {
				if (!Character.isDigit(c)) {
					end = i;
					break;
				}
			}
		}
		if (start == -1 || start == end) {
			throw new IllegalArgumentException("no digits found in string: '" + text + "'");
		}
		return text.substring(start, end);
	}

	public static boolean isInteger(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isLong(String text) {
		try {
			Long.parseLong(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isByte(String text) {
		try {
			Byte.parseByte(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isShort(String text) {
		try {
			Short.parseShort(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isFloat(String text) {
		try {
			Float.parseFloat(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isDouble(String text) {
		try {
			Double.parseDouble(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Returns the number of digits in the given value (ignores sign).
	 * @param value the value.
	 * @return the number of digits.
	 */
	public static int countDigits(long value) {
		value = Math.abs(value);
		int digits = 1;
		while (value >= 10) {
			value /= 10;
			digits++;
		}
		return digits;
	}

}
