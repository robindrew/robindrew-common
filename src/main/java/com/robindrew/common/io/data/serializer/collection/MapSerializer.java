package com.robindrew.common.io.data.serializer.collection;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataSerializer;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.serializer.ObjectSerializer;

public class MapSerializer<K, V> extends ObjectSerializer<Map<K, V>> {

	private final IDataSerializer<K> keySerializer;
	private final IDataSerializer<V> valueSerializer;

	public MapSerializer(IDataSerializer<K> keySerializer, IDataSerializer<V> valueSerializer, boolean nullable) {
		super(nullable);
		this.keySerializer = keySerializer;
		this.valueSerializer = valueSerializer;
	}

	protected Map<K, V> newMap(int size) {
		return new LinkedHashMap<K, V>(size);
	}

	@Override
	protected Map<K, V> readValue(IDataReader reader) throws IOException {
		int size = reader.readPositiveInt();
		Map<K, V> map = newMap(size);
		for (int i = 0; i < size; i++) {
			K key = reader.readObject(isNullable(), keySerializer);
			V value = reader.readObject(isNullable(), valueSerializer);
			map.put(key, value);
		}
		return map;
	}

	@Override
	protected void writeValue(IDataWriter writer, Map<K, V> map) throws IOException {
		writer.writePositiveInt(map.size());
		for (Entry<K, V> entry : map.entrySet()) {
			writer.writeObject(entry.getKey(), isNullable(), keySerializer);
			writer.writeObject(entry.getValue(), isNullable(), valueSerializer);
		}
	}

}
