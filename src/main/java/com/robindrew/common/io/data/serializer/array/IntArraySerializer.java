package com.robindrew.common.io.data.serializer.array;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class IntArraySerializer extends ObjectSerializer<int[]> {

	public IntArraySerializer(boolean nullable) {
		super(nullable);
	}

	@Override
	protected int[] readValue(IDataReader reader) throws IOException {
		int length = reader.readPositiveInt();
		int[] array = new int[length];
		for (int i = 0; i < length; i++) {
			array[i] = reader.readInt();
		}
		return array;
	}

	@Override
	protected void writeValue(IDataWriter writer, int[] array) throws IOException {
		writer.writePositiveInt(array.length);
		for (int element : array) {
			writer.writeInt(element);
		}
	}

}
