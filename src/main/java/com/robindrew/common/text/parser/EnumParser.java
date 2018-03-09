package com.robindrew.common.text.parser;

public class EnumParser<E extends Enum<E>> extends ObjectParser<E> {

	private final Class<E> type;

	public EnumParser(Class<E> type) {
		this.type = type;
	}

	@Override
	protected E parseObject(String text) {
		return Enum.valueOf(type, text);
	}

}
