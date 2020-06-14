package com.robindrew.common.io.data.serializer.collection;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataSerializer;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class SetSerializer<E> extends ObjectSerializer<Set<E>> {

	private final IDataSerializer<E> serializer;

	public SetSerializer(IDataSerializer<E> serializer, boolean nullable) {
		super(nullable);
		this.serializer = serializer;
	}

	protected Set<E> newSet(int size) {
		return new LinkedHashSet<E>(size);
	}

	@Override
	protected Set<E> readValue(IDataReader reader) throws IOException {
		int size = reader.readPositiveInt();
		Set<E> set = newSet(size);
		for (int i = 0; i < size; i++) {
			E element = reader.readObject(isNullable(), serializer);
			set.add(element);
		}
		return set;
	}

	@Override
	protected void writeValue(IDataWriter writer, Set<E> list) throws IOException {
		writer.writePositiveInt(list.size());
		for (E value : list) {
			writer.writeObject(value, isNullable(), serializer);
		}
	}

}
