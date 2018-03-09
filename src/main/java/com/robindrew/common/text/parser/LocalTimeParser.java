package com.robindrew.common.text.parser;

import java.time.LocalTime;

public class LocalTimeParser extends ObjectParser<LocalTime> {

	@Override
	protected LocalTime parseObject(String text) {
		return LocalTime.parse(text);
	}
}
