package com.robindrew.common.text.parser;

public class ShortParser extends ObjectParser<Short> {

	@Override
	protected Short parseObject(String text) {
		return Short.parseShort(text);
	}

}
