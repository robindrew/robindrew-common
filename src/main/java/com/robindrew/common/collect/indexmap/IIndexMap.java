package com.robindrew.common.collect.indexmap;

import java.util.List;
import java.util.Map;

public interface IIndexMap<V> {

	int size();

	boolean isEmpty();

	void clear();

	V get(int index);

	V get(int index, V defaultValue);

	V put(int index, V value);

	void putAll(Map<Integer, V> indexToValueMap, boolean clear);

	void putAll(Map<Integer, V> indexToValueMap);

	void putAll(IIndexMap<V> map);

	void putAll(V[] array);

	boolean putIfAbsent(int index, V value);

	boolean containsKey(int index);

	void remove(int index);

	List<V> values();

	V[] toArray();

}
