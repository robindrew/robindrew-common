package com.robindrew.common.io.data.serializer.lang;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class IntegerSerializer extends ObjectSerializer<Integer> {

	public IntegerSerializer(boolean nullable) {
		super(nullable);
	}

	@Override
	public Integer readValue(IDataReader reader) throws IOException {
		return reader.readInt();
	}

	@Override
	public void writeValue(IDataWriter writer, Integer value) throws IOException {
		writer.writeInt(value);
	}

}
