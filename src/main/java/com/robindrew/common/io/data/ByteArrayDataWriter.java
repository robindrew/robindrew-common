package com.robindrew.common.io.data;

import java.io.ByteArrayOutputStream;

public class ByteArrayDataWriter extends DataWriter {

	private final ByteArrayOutputStream output;

	public ByteArrayDataWriter(ByteArrayOutputStream output) {
		super(output);
		if (output == null) {
			throw new NullPointerException("output");
		}
		this.output = output;
	}

	public ByteArrayDataWriter(int size) {
		this(new ByteArrayOutputStream(size));
	}

	public ByteArrayDataWriter() {
		this(new ByteArrayOutputStream());
	}

	public byte[] toByteArray() {
		return output.toByteArray();
	}

}
