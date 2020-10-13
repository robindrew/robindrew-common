package com.robindrew.common.io.persister;

import java.io.IOException;

import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;

public class ByteArrayPersister implements IObjectPersister<byte[]> {

	@Override
	public void writeTo(ByteSink sink, byte[] value) throws IOException {
		sink.write(value);
	}

	@Override
	public byte[] readFrom(ByteSource source) throws IOException {
		return source.read();
	}

}
