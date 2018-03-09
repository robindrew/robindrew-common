package com.robindrew.common.collect.indexmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ArrayUtils;

/**
 * A thread safe map from an integer to an object, with extremely fast reads.
 * <p>
 * Requires that the indexes are sequential and limited to relatively low integer values.
 * <p>
 * @param <V> the indexed object type.
 */
@SuppressWarnings("unchecked")
public class ArrayIndexMap<V> implements IIndexMap<V> {

	private volatile V[] array;

	public ArrayIndexMap() {
		this(0);
	}

	public ArrayIndexMap(int capacity) {
		if (capacity == 0) {
			array = (V[]) ArrayUtils.EMPTY_OBJECT_ARRAY;
		} else {
			array = (V[]) new Object[capacity];
		}
	}

	@Override
	public int size() {
		return array.length;
	}

	@Override
	public boolean isEmpty() {
		return array.length == 0;
	}

	@Override
	public void clear() {
		array = (V[]) ArrayUtils.EMPTY_OBJECT_ARRAY;
	}

	@Override
	public V get(int index) {
		return get(index, null);
	}

	@Override
	public V get(int index, V defaultValue) {
		if (index < 0) {
			throw new IllegalArgumentException("index=" + index);
		}
		V[] array = this.array;
		if (index >= array.length) {
			return defaultValue;
		}
		V value = array[index];
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	@Override
	public V put(int index, V value) {
		if (index < 0) {
			throw new IllegalArgumentException("index=" + index);
		}
		if (value == null) {
			throw new NullPointerException("value");
		}

		V oldValue = null;

		// Only ever grows, never shrinks
		synchronized (this) {
			V[] oldArray = this.array;
			if (index < oldArray.length) {
				oldValue = oldArray[index];
			}

			// Create new array
			int newLength = (index < oldArray.length ? oldArray.length : index + 1);
			V[] newArray = (V[]) new Object[newLength];

			// Transfer old values
			System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);

			// Put new value
			newArray[index] = value;

			this.array = newArray;
		}

		return oldValue;
	}

	@Override
	public boolean putIfAbsent(int index, V value) {
		if (index < 0) {
			throw new IllegalArgumentException("index=" + index);
		}
		if (value == null) {
			throw new NullPointerException("value");
		}

		// Only ever grows, never shrinks
		synchronized (this) {
			V[] oldArray = this.array;
			if (index < oldArray.length && oldArray[index] != null) {
				return false;
			}

			// Create new array
			int newLength = (index < oldArray.length ? oldArray.length : index + 1);
			V[] newArray = (V[]) new Object[newLength];

			// Transfer old values
			System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);

			// Put new value
			newArray[index] = value;

			this.array = newArray;
			return true;
		}
	}

	@Override
	public List<V> values() {
		List<V> values = new ArrayList<>();
		for (V value : array) {
			if (value != null) {
				values.add(value);
			}
		}
		return values;
	}

	@Override
	public boolean containsKey(int index) {
		return get(index) != null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		boolean comma = false;
		builder.append('{');
		V[] array = this.array;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				if (comma) {
					builder.append(',');
				}
				comma = true;
				builder.append(i).append('=');
				builder.append(array[i]);
			}
		}
		builder.append('}');
		return builder.toString();
	}

	@Override
	public void remove(int index) {
		if (index < 0) {
			throw new IllegalArgumentException("index=" + index);
		}
		V[] array = this.array;
		if (index < array.length) {
			array[index] = null;
		}
	}

	@Override
	public void putAll(Map<Integer, V> indexToValueMap, boolean clear) {
		if (indexToValueMap.isEmpty()) {
			return;
		}
		synchronized (this) {
			if (clear) {
				clear();
			}
			putAll(indexToValueMap);
		}
	}

	/**
	 * This method is optimised to put large numbers of entries in to the map.
	 * <p>
	 * Although it does a double pass over the entries in the map, it is still orders of magnitude faster for large
	 * maps.
	 */
	@Override
	public void putAll(Map<Integer, V> indexToValueMap) {
		if (indexToValueMap.isEmpty()) {
			return;
		}

		// Only ever grows, never shrinks
		synchronized (this) {
			V[] oldArray = this.array;

			// Get maximum index
			int maxIndex = 0;
			for (Entry<Integer, V> entry : indexToValueMap.entrySet()) {
				V value = entry.getValue();
				if (value == null) {
					continue;
				}
				int index = entry.getKey();
				if (index < 0) {
					throw new IllegalArgumentException("indexToValueMap contains negative index: " + entry);
				}
				if (maxIndex < index) {
					maxIndex = index;
				}
			}

			// Create new array
			int newLength = (maxIndex < oldArray.length ? oldArray.length : maxIndex + 1);
			V[] newArray = (V[]) new Object[newLength];

			// Transfer old values
			if (oldArray.length > 0) {
				System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
			}

			// Put new values
			for (Entry<Integer, V> entry : indexToValueMap.entrySet()) {
				V value = entry.getValue();
				if (value == null) {
					continue;
				}
				int index = entry.getKey();
				newArray[index] = value;
			}

			// Done!
			this.array = newArray;
		}
	}

	@Override
	public V[] toArray() {
		V[] array = this.array;
		if (array.length == 0) {
			return array;
		}
		V[] newArray = (V[]) new Object[array.length];
		System.arraycopy(array, 0, newArray, 0, array.length);
		return newArray;
	}

	@Override
	public void putAll(IIndexMap<V> map) {
		if (map.isEmpty()) {
			return;
		}
		putAll(map.toArray());
	}

	@Override
	public void putAll(V[] putArray) {
		if (putArray.length == 0) {
			return;
		}

		// Only ever grows, never shrinks
		synchronized (this) {
			V[] oldArray = this.array;

			int newLength = Math.max(oldArray.length, putArray.length);
			V[] newArray = (V[]) new Object[newLength];

			// Copy old array directly
			System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);

			// Only set non null values from new array, do not overwrite old
			// values ... (union)
			for (int i = 0; i < putArray.length; i++) {
				V value = putArray[i];
				if (value != null) {
					newArray[i] = value;
				}
			}

			this.array = newArray;
		}
	}

}
