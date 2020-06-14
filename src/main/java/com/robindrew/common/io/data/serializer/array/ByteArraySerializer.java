package com.robindrew.common.io.data.serializer.array;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class ByteArraySerializer extends ObjectSerializer<byte[]> {

	public ByteArraySerializer(boolean nullable) {
		super(nullable);
	}

	@Override
	protected byte[] readValue(IDataReader reader) throws IOException {
		int length = reader.readPositiveInt();
		byte[] array = new byte[length];
		for (int i = 0; i < length; i++) {
			array[i] = reader.readByte();
		}
		return array;
	}

	@Override
	protected void writeValue(IDataWriter writer, byte[] array) throws IOException {
		writer.writePositiveInt(array.length);
		for (byte element : array) {
			writer.writeByte(element);
		}
	}

}
