package com.robindrew.common.text.parser;

public class StringArrayParser extends ArrayParser<String> {

	@Override
	protected String[] newArray(int length) {
		return new String[length];
	}

	@Override
	protected String parseElement(String text) {
		return text;
	}

}
