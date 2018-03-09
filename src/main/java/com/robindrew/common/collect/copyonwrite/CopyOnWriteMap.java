package com.robindrew.common.collect.copyonwrite;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableSet;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CopyOnWriteMap<K, V> implements Map<K, V> {

	private volatile Map<K, V> map = emptyMap();

	protected Map<K, V> newMap(Map<K, V> map) {
		// Override to create different maps
		return new LinkedHashMap<>(map);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}

	@Override
	public Set<K> keySet() {
		return unmodifiableSet(map.keySet());
	}

	@Override
	public Collection<V> values() {
		return unmodifiableCollection(map.values());
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return unmodifiableSet(map.entrySet());
	}

	@Override
	public boolean equals(Object object) {
		return map.equals(object);
	}

	@Override
	public int hashCode() {
		return map.hashCode();
	}

	@Override
	public V put(K key, V value) {
		synchronized (this) {
			Map<K, V> newMap = newMap(map);
			V returnValue = newMap.put(key, value);
			this.map = newMap;
			return returnValue;
		}
	}

	@Override
	public V remove(Object key) {
		synchronized (this) {
			Map<K, V> newMap = newMap(map);
			V returnValue = newMap.remove(key);
			this.map = newMap;
			return returnValue;
		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> putMap) {
		synchronized (this) {
			Map<K, V> newMap = newMap(map);
			newMap.putAll(putMap);
			this.map = newMap;
		}
	}

	@Override
	public void clear() {
		synchronized (this) {
			this.map = newMap(emptyMap());
		}
	}
}
