package com.robindrew.common.io.data.serializer.lang;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class CharacterSerializer extends ObjectSerializer<Character> {

	public CharacterSerializer(boolean nullable) {
		super(nullable);
	}

	@Override
	public Character readValue(IDataReader reader) throws IOException {
		return reader.readChar();
	}

	@Override
	public void writeValue(IDataWriter writer, Character value) throws IOException {
		writer.writeChar(value);
	}

}
