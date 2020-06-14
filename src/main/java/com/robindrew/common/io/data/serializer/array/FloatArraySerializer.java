package com.robindrew.common.io.data.serializer.array;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class FloatArraySerializer extends ObjectSerializer<float[]> {

	public FloatArraySerializer(boolean nullable) {
		super(nullable);
	}

	@Override
	protected float[] readValue(IDataReader reader) throws IOException {
		int length = reader.readPositiveInt();
		float[] array = new float[length];
		for (int i = 0; i < length; i++) {
			array[i] = reader.readFloat();
		}
		return array;
	}

	@Override
	protected void writeValue(IDataWriter writer, float[] array) throws IOException {
		writer.writePositiveInt(array.length);
		for (float element : array) {
			writer.writeFloat(element);
		}
	}

}
