package com.robindrew.common.io.data.serializer.lang;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class BooleanSerializer extends ObjectSerializer<Boolean> {

	public BooleanSerializer(boolean nullable) {
		super(nullable);
	}

	@Override
	public Boolean readValue(IDataReader reader) throws IOException {
		return reader.readBoolean();
	}

	@Override
	public void writeValue(IDataWriter writer, Boolean value) throws IOException {
		writer.writeBoolean(value);
	}

}
