package com.robindrew.common.io.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataListSerializer<O> implements IDataSerializer<List<O>> {

	private final IDataSerializer<O> serializer;

	public DataListSerializer(IDataSerializer<O> serializer) {
		if (serializer == null) {
			throw new NullPointerException("serializer");
		}
		this.serializer = serializer;
	}

	protected List<O> newList(int initialCapacity) {
		return new ArrayList<O>(initialCapacity);
	}

	@Override
	public List<O> readObject(IDataReader reader) throws IOException {
		int length = reader.readPositiveInt();
		List<O> list = newList(length);
		for (int i = 0; i < length; i++) {
			O element = serializer.readObject(reader);
			list.add(element);
		}
		return list;
	}

	@Override
	public void writeObject(IDataWriter writer, List<O> list) throws IOException {
		writer.writePositiveInt(list.size());
		for (O element : list) {
			serializer.writeObject(writer, element);
		}
	}

}
