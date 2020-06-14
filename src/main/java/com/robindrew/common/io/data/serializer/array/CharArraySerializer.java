package com.robindrew.common.io.data.serializer.array;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class CharArraySerializer extends ObjectSerializer<char[]> {

	public CharArraySerializer(boolean nullable) {
		super(nullable);
	}

	@Override
	protected char[] readValue(IDataReader reader) throws IOException {
		int length = reader.readPositiveInt();
		char[] array = new char[length];
		for (int i = 0; i < length; i++) {
			array[i] = reader.readChar();
		}
		return array;
	}

	@Override
	protected void writeValue(IDataWriter writer, char[] array) throws IOException {
		writer.writePositiveInt(array.length);
		for (char element : array) {
			writer.writeChar(element);
		}
	}

}
