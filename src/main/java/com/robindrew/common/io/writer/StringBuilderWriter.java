package com.robindrew.common.io.writer;

import java.io.IOException;
import java.io.Writer;

public class StringBuilderWriter implements IWriter {

	private final StringBuilder builder = new StringBuilder();

	@Override
	public IWriter append(char value) {
		builder.append(value);
		return this;
	}

	@Override
	public IWriter append(CharSequence value) {
		builder.append(value);
		return this;
	}

	@Override
	public IWriter append(Object value) {
		builder.append(value);
		return this;
	}

	@Override
	public String toString() {
		return builder.toString();
	}

	@Override
	public IWriter append(char[] value) {
		builder.append(value);
		return this;
	}

	@Override
	public IWriter append(char[] value, int offset, int length) {
		builder.append(value, offset, length);
		return this;
	}

	@Override
	public IWriter append(CharSequence value, int offset, int length) {
		builder.append(value, offset, length);
		return this;
	}

	@Override
	public void flush() {
		// Nothing to do
	}

	@Override
	public void close() {
		// Nothing to do
	}

	@Override
	public Writer asWriter() {
		return new InnerWriter();
	}

	private class InnerWriter extends Writer {

		@Override
		public void write(char[] array, int offset, int length) throws IOException {
			builder.append(array, offset, length);
		}

		@Override
		public void flush() throws IOException {
			// Nothing to do
		}

		@Override
		public void close() throws IOException {
			// Nothing to do
		}
	}

}
