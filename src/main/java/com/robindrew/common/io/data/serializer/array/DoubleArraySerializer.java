package com.robindrew.common.io.data.serializer.array;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class DoubleArraySerializer extends ObjectSerializer<double[]> {

	public DoubleArraySerializer(boolean nullable) {
		super(nullable);
	}

	@Override
	protected double[] readValue(IDataReader reader) throws IOException {
		int length = reader.readPositiveInt();
		double[] array = new double[length];
		for (int i = 0; i < length; i++) {
			array[i] = reader.readDouble();
		}
		return array;
	}

	@Override
	protected void writeValue(IDataWriter writer, double[] array) throws IOException {
		writer.writePositiveInt(array.length);
		for (double element : array) {
			writer.writeDouble(element);
		}
	}

}
