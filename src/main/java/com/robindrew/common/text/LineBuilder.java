package com.robindrew.common.text;

import com.robindrew.common.util.Java;

public class LineBuilder implements ILineBuilder {

	private final StringBuilder builder;
	private String lineSeparator = Java.getLineSeparator();
	private int separatorIndex = 0;

	public LineBuilder() {
		builder = new StringBuilder();
	}

	public LineBuilder(CharSequence text) {
		builder = new StringBuilder(text);
	}

	public LineBuilder(String text) {
		builder = new StringBuilder(text);
	}

	public LineBuilder(int capacity) {
		builder = new StringBuilder(capacity);
	}

	@Override
	public void setLineSeparator(String separator) {
		if (separator.isEmpty()) {
			throw new IllegalArgumentException("separator is empty");
		}
		this.lineSeparator = separator;
	}

	@Override
	public int length() {
		return builder.length();
	}

	@Override
	public char charAt(int index) {
		return builder.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return builder.subSequence(start, end);
	}

	@Override
	public ILineBuilder append(Object value) {
		builder.append(value);
		return this;
	}

	@Override
	public ILineBuilder append(String value) {
		builder.append(value);
		return this;
	}

	@Override
	public ILineBuilder append(byte value) {
		builder.append(value);
		return this;
	}

	@Override
	public ILineBuilder append(short value) {
		builder.append(value);
		return this;
	}

	@Override
	public ILineBuilder append(int value) {
		builder.append(value);
		return this;
	}

	@Override
	public ILineBuilder append(long value) {
		builder.append(value);
		return this;
	}

	@Override
	public ILineBuilder append(float value) {
		builder.append(value);
		return this;
	}

	@Override
	public ILineBuilder append(double value) {
		builder.append(value);
		return this;
	}

	@Override
	public ILineBuilder append(boolean value) {
		builder.append(value);
		return this;
	}

	@Override
	public ILineBuilder append(char value) {
		builder.append(value);
		return this;
	}

	@Override
	public ILineBuilder append(char[] value) {
		builder.append(value);
		return this;
	}

	@Override
	public ILineBuilder append(CharSequence value) {
		builder.append(value);
		return this;
	}

	@Override
	public ILineBuilder append(char[] value, int offset, int length) {
		builder.append(value, offset, length);
		return this;
	}

	@Override
	public ILineBuilder append(CharSequence value, int start, int end) {
		builder.append(value, start, end);
		return this;
	}

	@Override
	public ILineBuilder appendLine() {
		builder.append(lineSeparator);
		return this;
	}

	@Override
	public ILineBuilder appendLine(Object value) {
		append(value);
		return appendLine();
	}

	@Override
	public ILineBuilder appendLine(String value) {
		append(value);
		return appendLine();
	}

	@Override
	public ILineBuilder appendLine(Object... values) {
		for (Object value : values) {
			append(value);
		}
		return appendLine();
	}

	@Override
	public ILineBuilder appendLine(String... values) {
		for (String value : values) {
			append(value);
		}
		return appendLine();
	}

	@Override
	public String toString() {
		return builder.toString();
	}

	@Override
	public ILineBuilder separator(String separator) {
		return separator(separator, 1);
	}

	@Override
	public ILineBuilder separator(String separator, int fromIndex) {
		if (fromIndex <= separatorIndex) {
			append(separator);
		}
		separatorIndex++;
		return this;
	}

	@Override
	public ILineBuilder clear() {
		builder.setLength(0);
		separatorIndex = 0;
		return this;
	}

}
