package com.robindrew.common.text.parser;

public class CharacterParser extends ObjectParser<Character> {

	@Override
	protected Character parseObject(String text) {
		if (text.length() != 1) {
			throw new IllegalArgumentException("text: '" + text + "'");
		}
		return text.charAt(0);
	}

}
