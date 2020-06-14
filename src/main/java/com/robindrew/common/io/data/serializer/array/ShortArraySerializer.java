package com.robindrew.common.io.data.serializer.array;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class ShortArraySerializer extends ObjectSerializer<short[]> {

	public ShortArraySerializer(boolean nullable) {
		super(nullable);
	}

	@Override
	protected short[] readValue(IDataReader reader) throws IOException {
		int length = reader.readPositiveInt();
		short[] array = new short[length];
		for (int i = 0; i < length; i++) {
			array[i] = reader.readShort();
		}
		return array;
	}

	@Override
	protected void writeValue(IDataWriter writer, short[] array) throws IOException {
		writer.writePositiveInt(array.length);
		for (short element : array) {
			writer.writeShort(element);
		}
	}

}
