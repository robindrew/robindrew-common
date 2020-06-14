package com.robindrew.common.io.data.serializer.lang;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class EnumSerializer<E extends Enum<E>> extends ObjectSerializer<E> {

	private final Class<E> enumClass;
	
	public EnumSerializer(Class<E> enumClass, boolean nullable) {
		super(nullable);
		this.enumClass = enumClass;
	}

	@Override
	public E readValue(IDataReader reader) throws IOException {
		int ordinal = reader.readPositiveInt();
		return enumClass.getEnumConstants()[ordinal];
	}

	@Override
	public void writeValue(IDataWriter writer, E value) throws IOException {
		int ordinal = value.ordinal();
		writer.writePositiveInt(ordinal);
	}

}
