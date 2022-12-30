package com.robindrew.common.io.data.serializer;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataSerializer;
import com.robindrew.common.io.data.IDataWriter;

public abstract class ObjectSerializer<O> implements IDataSerializer<O> {

	private final boolean nullable;

	protected ObjectSerializer(boolean nullable) {
		this.nullable = nullable;
	}

	public boolean isNullable() {
		return nullable;
	}

	@Override
	public O readObject(IDataReader reader) throws IOException {
		if (isNullable() && reader.readNull()) {
			return null;
		}
		return readValue(reader);
	}

	@Override
	public void writeObject(IDataWriter writer, O value) throws IOException {
		if (isNullable()) {
			if (value == null) {
				writer.writeNull(true);
				return;
			}
			writer.writeNull(false);
		}
		writeValue(writer, value);
	}

	protected abstract O readValue(IDataReader reader) throws IOException;

	protected abstract void writeValue(IDataWriter writer, O value) throws IOException;

}
