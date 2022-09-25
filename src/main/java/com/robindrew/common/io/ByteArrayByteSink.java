package com.robindrew.common.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.google.common.io.ByteSink;

public class ByteArrayByteSink extends ByteSink {

	private static final byte[] EMPTY = new byte[0];

	private byte[] bytes = EMPTY;
	private ByteArrayOutputStream output = null;

	@Override
	public OutputStream openStream() throws IOException {
		flush();
		output = new ByteArrayOutputStream();
		return output;
	}

	private void flush() {
		if (output != null) {
			bytes = output.toByteArray();
			output = null;
		}
	}

	public byte[] toByteArray() {
		flush();
		return bytes;
	}

}
