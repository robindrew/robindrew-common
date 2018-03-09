package com.robindrew.common.collect.count;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A Count Map.
 * @param <K> the key type.
 */
public class CountMap<K> implements ICountMap<K> {

	/** The key to count map. */
	private final Map<K, Count> keyToCountMap;

	/**
	 * Creates a new count map.
	 * @param map the map.
	 */
	public CountMap(Map<K, Count> map) {
		if (map == null) {
			throw new NullPointerException();
		}
		this.keyToCountMap = map;
	}

	/**
	 * Creates a new count map.
	 */
	public CountMap() {
		this(new HashMap<K, Count>());
	}

	@Override
	public Set<K> keySet() {
		return keyToCountMap.keySet();
	}

	/**
	 * Returns an iterator over the keys in this map.
	 * @return an iterator over the keys in this map.
	 */
	@Override
	public Iterator<K> iterator() {
		return keySet().iterator();
	}

	@Override
	public long get(K key) {
		Count count = keyToCountMap.get(key);
		if (count == null) {
			return 0;
		}
		return count.get();
	}

	@Override
	public Count getCount(K key) {
		if (key == null) {
			throw new NullPointerException();
		}
		Count count;
		synchronized (keyToCountMap) {
			count = keyToCountMap.get(key);
			if (count == null) {
				count = new Count();
				keyToCountMap.put(key, count);
			}
		}
		return count;
	}

	@Override
	public long increment(K key) {
		return getCount(key).increment();
	}

	@Override
	public long increment(K key, long amount) {
		return getCount(key).increment(amount);
	}

	@Override
	public long decrement(K key) {
		return getCount(key).decrement();
	}

	@Override
	public long decrement(K key, long amount) {
		if (amount < 1) {
			throw new IllegalArgumentException("amount=" + amount);
		}
		return getCount(key).decrement(amount);
	}

	@Override
	public String toString() {
		return keyToCountMap.toString();
	}

	@Override
	public List<ICountEntry<K>> toList() {
		List<ICountEntry<K>> list = new ArrayList<ICountEntry<K>>();
		for (Entry<K, Count> entry : keyToCountMap.entrySet()) {
			ICountEntry<K> count = new CountEntry<K>(entry.getKey(), entry.getValue().get());
			list.add(count);
		}
		return list;
	}

	@Override
	public boolean isEmpty() {
		return keyToCountMap.isEmpty();
	}

	@Override
	public int size() {
		return keyToCountMap.size();
	}

	@Override
	public Map<K, Long> toMap() {
		Map<K, Long> map = new LinkedHashMap<K, Long>();
		for (Entry<K, Count> entry : keyToCountMap.entrySet()) {
			map.put(entry.getKey(), entry.getValue().get());
		}
		return map;
	}

}
