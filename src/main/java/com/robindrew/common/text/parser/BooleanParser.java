package com.robindrew.common.text.parser;

public class BooleanParser extends ObjectParser<Boolean> {

	@Override
	protected Boolean parseObject(String text) {
		return new Boolean(text);
	}

}
