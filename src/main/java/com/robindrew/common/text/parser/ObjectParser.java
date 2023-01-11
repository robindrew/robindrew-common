package com.robindrew.common.text.parser;

public abstract class ObjectParser<P> implements IStringParser<P> {

	private boolean nullValid = true;

	public boolean isNullValid() {
		return nullValid;
	}

	public ObjectParser<P> setNullValid(boolean nullValid) {
		this.nullValid = nullValid;
		return this;
	}

	@Override
	public final P parse(String text) {
		if (text == null) {
			if (isNullValid()) {
				return null;
			}
			throw new NullPointerException("text");
		}
		return parseObject(text);
	}

	protected abstract P parseObject(String text);

}
