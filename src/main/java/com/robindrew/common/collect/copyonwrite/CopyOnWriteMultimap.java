package com.robindrew.common.collect.copyonwrite;

import static com.google.common.collect.LinkedHashMultimap.create;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

/**
 * Thread safe {@link Multimap}, optimal where read operations are frequent and write operations infrequent.
 */
public class CopyOnWriteMultimap<K, V> implements Multimap<K, V> {

	private volatile Multimap<K, V> map = newMap();

	protected Multimap<K, V> newMap() {
		// Override to create different maps
		return create();
	}

	@Override
	public Map<K, Collection<V>> asMap() {
		return map.asMap();
	}

	@Override
	public boolean containsEntry(Object key, Object value) {
		return map.containsEntry(key, value);
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
	public Collection<Entry<K, V>> entries() {
		return map.entries();
	}

	@Override
	public boolean equals(Object object) {
		return map.equals(object);
	}

	@Override
	public Collection<V> get(K key) {
		return map.get(key);
	}

	@Override
	public int hashCode() {
		return map.hashCode();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public Multiset<K> keys() {
		return map.keys();
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public boolean put(K key, V value) {
		synchronized (this) {
			Multimap<K, V> newMap = newMap();
			newMap.putAll(map);
			boolean returnValue = newMap.put(key, value);
			this.map = newMap;
			return returnValue;
		}
	}

	@Override
	public boolean putAll(K key, Iterable<? extends V> values) {
		synchronized (this) {
			Multimap<K, V> newMap = newMap();
			newMap.putAll(map);
			boolean returnValue = newMap.putAll(key, values);
			this.map = newMap;
			return returnValue;
		}
	}

	@Override
	public boolean putAll(Multimap<? extends K, ? extends V> multiMap) {
		synchronized (this) {
			Multimap<K, V> newMap = newMap();
			newMap.putAll(map);
			boolean returnValue = newMap.putAll(multiMap);
			this.map = newMap;
			return returnValue;
		}
	}

	@Override
	public boolean remove(Object key, Object value) {
		synchronized (this) {
			Multimap<K, V> newMap = newMap();
			newMap.putAll(map);
			boolean returnValue = newMap.remove(key, value);
			this.map = newMap;
			return returnValue;
		}
	}

	@Override
	public Collection<V> removeAll(Object key) {
		synchronized (this) {
			Multimap<K, V> newMap = newMap();
			newMap.putAll(map);
			Collection<V> returnValue = newMap.removeAll(key);
			this.map = newMap;
			return returnValue;
		}
	}

	@Override
	public Collection<V> replaceValues(K key, Iterable<? extends V> values) {
		synchronized (this) {
			Multimap<K, V> newMap = newMap();
			newMap.putAll(map);
			Collection<V> returnValue = newMap.replaceValues(key, values);
			this.map = newMap;
			return returnValue;
		}
	}

	@Override
	public void clear() {
		synchronized (this) {
			this.map = newMap();
		}
	}

}
