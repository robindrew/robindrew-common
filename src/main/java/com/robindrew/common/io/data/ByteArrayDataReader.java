package com.robindrew.common.io.data;

import java.io.ByteArrayInputStream;

public class ByteArrayDataReader extends DataReader {

	private final ByteArrayInputStream input;

	public ByteArrayDataReader(ByteArrayInputStream input) {
		super(input);
		if (input == null) {
			throw new NullPointerException("input");
		}
		this.input = input;
	}

	public ByteArrayDataReader(byte[] data, int offset, int length) {
		this(new ByteArrayInputStream(data, offset, length));
	}

	public ByteArrayDataReader(byte[] data) {
		this(new ByteArrayInputStream(data));
	}

	public ByteArrayInputStream getInput() {
		return input;
	}

}
