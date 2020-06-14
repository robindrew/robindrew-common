package com.robindrew.common.io.data.serializer.array;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class BooleanArraySerializer extends ObjectSerializer<boolean[]> {

	public BooleanArraySerializer(boolean nullable) {
		super(nullable);
	}

	@Override
	protected boolean[] readValue(IDataReader reader) throws IOException {
		int length = reader.readPositiveInt();
		boolean[] array = new boolean[length];
		for (int i = 0; i < length; i++) {
			array[i] = reader.readBoolean();
		}
		return array;
	}

	@Override
	protected void writeValue(IDataWriter writer, boolean[] array) throws IOException {
		writer.writePositiveInt(array.length);
		for (boolean element : array) {
			writer.writeBoolean(element);
		}
	}

}
