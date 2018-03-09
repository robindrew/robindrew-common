package com.robindrew.common.io.stream;

import java.io.Reader;
import java.io.StringReader;

import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;

public class StringStreamInput extends StreamInput {

	private final String text;

	public StringStreamInput(String text) {
		if (text == null) {
			throw new NullPointerException("text");
		}
		this.text = text;

		// Buffering unnecessary
		setBufferSize(0);
	}

	@Override
	public byte[] readToByteArray() {
		return text.getBytes(getCharset());
	}

	@Override
	public Reader newReader() {
		return new StringReader(text);
	}

	@Override
	public CharSource asCharSource() {
		return CharSource.wrap(text);
	}

	@Override
	public ByteSource asByteSource() {
		return ByteSource.wrap(readToByteArray());
	}

}
