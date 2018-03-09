package com.robindrew.common.text.parser;

public class ByteParser extends ObjectParser<Byte> {

	@Override
	protected Byte parseObject(String text) {
		return Byte.parseByte(text);
	}

}
