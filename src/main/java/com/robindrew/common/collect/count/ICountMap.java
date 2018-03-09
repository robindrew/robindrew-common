package com.robindrew.common.collect.count;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ICountMap<K> extends Iterable<K> {

	/**
	 * Returns the key set.
	 * @return the key set.
	 */
	Set<K> keySet();

	/**
	 * Returns the amount for the given key.
	 * @param key the key.
	 * @return the amount.
	 */
	long get(K key);

	/**
	 * Returns the count for the given key.
	 * @param key the key.
	 * @return the count.
	 */
	Count getCount(K key);

	/**
	 * Increment the count for the given key.
	 * @param key the key.
	 */
	long increment(K key);

	/**
	 * Increment the count for the given key by the given amount.
	 * @param key the key.
	 * @param amount the amount (a non-zero, positive integer).
	 */
	long increment(K key, long amount);

	/**
	 * Decrement the count for the given key.
	 * @param key the key.
	 */
	long decrement(K key);

	/**
	 * Decrement the count for the given key by the given amount.
	 * @param key the key.
	 * @param amount the amount (a non-zero, positive integer).
	 */
	long decrement(K key, long amount);

	/**
	 * Returns the size of the map.
	 * @return the size of the map.
	 */
	int size();

	/**
	 * Returns true if the map is empty.
	 * @return true if the map is empty.
	 */
	boolean isEmpty();

	/**
	 * Returns this count map as a list.
	 * @return this count map as a list.
	 */
	List<ICountEntry<K>> toList();

	/**
	 * Returns this count map as a map.
	 * @return this count map as a map.
	 */
	Map<K, Long> toMap();

}