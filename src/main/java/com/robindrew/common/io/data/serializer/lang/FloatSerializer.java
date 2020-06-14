package com.robindrew.common.io.data.serializer.lang;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class FloatSerializer extends ObjectSerializer<Float> {

	public FloatSerializer(boolean nullable) {
		super(nullable);
	}

	@Override
	public Float readValue(IDataReader reader) throws IOException {
		return reader.readFloat();
	}

	@Override
	public void writeValue(IDataWriter writer, Float value) throws IOException {
		writer.writeFloat(value);
	}

}
