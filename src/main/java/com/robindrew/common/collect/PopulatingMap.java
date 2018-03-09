package com.robindrew.common.collect;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import com.google.common.collect.ForwardingMap;

public class PopulatingMap<K, V> extends ForwardingMap<K, V> {

	public static <K, V> PopulatingMap<K, V> createConcurrentMap(Function<K, V> function) {
		Map<K, V> map = new ConcurrentHashMap<>();
		return new PopulatingMap<>(map, function);
	}

	private final Map<K, V> map;
	private final Function<K, V> function;

	public PopulatingMap(Map<K, V> map, Function<K, V> function) {
		if (map == null) {
			throw new NullPointerException("map");
		}
		if (function == null) {
			throw new NullPointerException("function");
		}
		this.map = map;
		this.function = function;
	}

	@Override
	protected Map<K, V> delegate() {
		return map;
	}

	public V get(K key, boolean populateIfAbsent) {
		V value = get(key);
		if (value == null && populateIfAbsent) {
			if (!containsKey(key)) {
				putIfAbsent(key, function.apply(key));
				value = get(key);
			}
		}
		return value;
	}

}
