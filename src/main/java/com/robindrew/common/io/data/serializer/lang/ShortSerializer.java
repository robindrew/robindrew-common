package com.robindrew.common.io.data.serializer.lang;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class ShortSerializer extends ObjectSerializer<Short> {

	public ShortSerializer(boolean nullable) {
		super(nullable);
	}

	@Override
	public Short readValue(IDataReader reader) throws IOException {
		return reader.readShort();
	}

	@Override
	public void writeValue(IDataWriter writer, Short value) throws IOException {
		writer.writeShort(value);
	}

}
