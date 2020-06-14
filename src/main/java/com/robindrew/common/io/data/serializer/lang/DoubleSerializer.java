package com.robindrew.common.io.data.serializer.lang;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class DoubleSerializer extends ObjectSerializer<Double> {

	public DoubleSerializer(boolean nullable) {
		super(nullable);
	}

	@Override
	public Double readValue(IDataReader reader) throws IOException {
		return reader.readDouble();
	}

	@Override
	public void writeValue(IDataWriter writer, Double value) throws IOException {
		writer.writeDouble(value);
	}

}
