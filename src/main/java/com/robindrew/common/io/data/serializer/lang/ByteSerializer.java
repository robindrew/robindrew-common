package com.robindrew.common.io.data.serializer.lang;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class ByteSerializer extends ObjectSerializer<Byte> {

	public ByteSerializer(boolean nullable) {
		super(nullable);
	}

	@Override
	public Byte readValue(IDataReader reader) throws IOException {
		return reader.readByte();
	}

	@Override
	public void writeValue(IDataWriter writer, Byte value) throws IOException {
		writer.writeByte(value);
	}

}
