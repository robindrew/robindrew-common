package com.robindrew.common.io.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DataWriterSerializer {

	public IDataWriter newWriter(OutputStream output) {
		return new DataWriter(output);
	}

	public <T> byte[] toData(IDataSerializer<T> serializer, T object) throws IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		serializer.writeObject(newWriter(bytes), object);
		return bytes.toByteArray();
	}
}
