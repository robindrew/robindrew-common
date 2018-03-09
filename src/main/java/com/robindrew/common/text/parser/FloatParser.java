package com.robindrew.common.text.parser;

public class FloatParser extends ObjectParser<Float> {

	@Override
	protected Float parseObject(String text) {
		return Float.parseFloat(text);
	}

}
