package com.robindrew.common.io.data.serializer.lang;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class LongSerializer extends ObjectSerializer<Long> {

	public LongSerializer(boolean nullable) {
		super(nullable);
	}

	@Override
	public Long readValue(IDataReader reader) throws IOException {
		return reader.readLong();
	}

	@Override
	public void writeValue(IDataWriter writer, Long value) throws IOException {
		writer.writeLong(value);
	}

}
