package com.robindrew.common.text.parser;

public class IntegerParser extends ObjectParser<Integer> {

	@Override
	protected Integer parseObject(String text) {
		return Integer.parseInt(text);
	}

}
