package com.robindrew.common.text.parser;

public class DoubleParser extends ObjectParser<Double> {

	@Override
	protected Double parseObject(String text) {
		return Double.parseDouble(text);
	}

}
