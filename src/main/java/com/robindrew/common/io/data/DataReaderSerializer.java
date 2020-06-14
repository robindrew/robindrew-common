package com.robindrew.common.io.data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataReaderSerializer {

	public IDataReader newReader(InputStream input) {
		return new DataReader(input);
	}

	public <T> T fromData(IDataSerializer<T> serializer, byte[] bytes) throws IOException {
		ByteArrayInputStream input = new ByteArrayInputStream(bytes);
		IDataReader reader = newReader(input);
		return serializer.readObject(reader);
	}
}
