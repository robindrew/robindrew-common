package com.robindrew.common.text.parser;

import static java.lang.Integer.parseInt;

import java.time.LocalDate;

public class LocalDateParser extends ObjectParser<LocalDate> {

	public static LocalDate parseLocalDate(String text) {
		// Much faster alternative method
		if (text.length() != 10) {
			throw new IllegalArgumentException("badly formatted date: '" + text + "'");
		}
		if (text.charAt(4) != '-') {
			throw new IllegalArgumentException("badly formatted date: '" + text + "'");
		}
		if (text.charAt(7) != '-') {
			throw new IllegalArgumentException("badly formatted date: '" + text + "'");
		}

		int year = parseInt(text.substring(0, 4));
		int month = parseInt(text.substring(5, 7));
		int day = parseInt(text.substring(8, 10));

		return LocalDate.of(year, month, day);

	}

	@Override
	protected LocalDate parseObject(String text) {
		return LocalDate.parse(text);
	}
}
