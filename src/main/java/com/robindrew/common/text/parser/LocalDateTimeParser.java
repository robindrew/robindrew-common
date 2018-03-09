package com.robindrew.common.text.parser;

import java.time.LocalDateTime;

public class LocalDateTimeParser extends ObjectParser<LocalDateTime> {

	@Override
	protected LocalDateTime parseObject(String text) {
		return LocalDateTime.parse(text);
	}
}
