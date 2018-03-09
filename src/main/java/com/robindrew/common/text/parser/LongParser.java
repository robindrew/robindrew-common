package com.robindrew.common.text.parser;

public class LongParser extends ObjectParser<Long> {

	@Override
	protected Long parseObject(String text) {
		return Long.parseLong(text);
	}

}
