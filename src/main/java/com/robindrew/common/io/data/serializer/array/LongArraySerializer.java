package com.robindrew.common.io.data.serializer.array;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class LongArraySerializer extends ObjectSerializer<long[]> {

	public LongArraySerializer(boolean nullable) {
		super(nullable);
	}

	@Override
	protected long[] readValue(IDataReader reader) throws IOException {
		int length = reader.readPositiveInt();
		long[] array = new long[length];
		for (int i = 0; i < length; i++) {
			array[i] = reader.readLong();
		}
		return array;
	}

	@Override
	protected void writeValue(IDataWriter writer, long[] array) throws IOException {
		writer.writePositiveInt(array.length);
		for (long element : array) {
			writer.writeLong(element);
		}
	}

}
