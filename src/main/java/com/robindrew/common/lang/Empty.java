package com.robindrew.common.lang;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

/**
 * A convenience class for common empty structures.
 */
@SuppressWarnings("rawtypes")
public class Empty {

	public static final byte[] BYTE_ARRAY = new byte[0];
	public static final short[] SHORT_ARRAY = new short[0];
	public static final int[] INT_ARRAY = new int[0];
	public static final long[] LONG_ARRAY = new long[0];
	public static final float[] FLOAT_ARRAY = new float[0];
	public static final double[] DOUBLE_ARRAY = new double[0];
	public static final char[] CHAR_ARRAY = new char[0];
	public static final boolean[] BOOLEAN_ARRAY = new boolean[0];

	public static final List<?> LIST = Collections.EMPTY_LIST;
	public static final Set<?> SET = Collections.EMPTY_SET;
	public static final Map<?, ?> MAP = Collections.EMPTY_MAP;
	public static final Multimap<?, ?> MULTIMAP = new ImmutableMultimap.Builder().build();
	public static final BiMap<?, ?> BIMAP = new ImmutableBiMap.Builder().build();

	public static final Object[] ARRAY = new Object[0];
	public static final String[] STRING_ARRAY = new String[0];
	public static final File[] FILE_ARRAY = new File[0];

	private static final ConcurrentMap<Class<?>, Object> arrayCache = new ConcurrentHashMap<>();

	static {
		arrayCache.put(byte[].class, BYTE_ARRAY);
		arrayCache.put(short[].class, SHORT_ARRAY);
		arrayCache.put(int[].class, INT_ARRAY);
		arrayCache.put(long[].class, LONG_ARRAY);
		arrayCache.put(float[].class, FLOAT_ARRAY);
		arrayCache.put(double[].class, DOUBLE_ARRAY);
		arrayCache.put(char[].class, CHAR_ARRAY);
		arrayCache.put(boolean[].class, BOOLEAN_ARRAY);

		arrayCache.put(Object[].class, ARRAY);
		arrayCache.put(String[].class, STRING_ARRAY);
		arrayCache.put(File[].class, FILE_ARRAY);
	}

	public static final <K, V> Map<K, V> map() {
		return Collections.emptyMap();
	}

	public static final <E> Set<E> set() {
		return Collections.emptySet();
	}

	public static final <E> List<E> list() {
		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	public static final <A> A array(Class<?> type) {
		A array = (A) arrayCache.get(type);
		if (array == null) {
			if (!type.isArray()) {
				throw new IllegalArgumentException("not an array type: " + type);
			}
			array = (A) Array.newInstance(type.getComponentType(), 0);
			A exists = (A) arrayCache.putIfAbsent(type, array);
			if (exists != null) {
				array = exists;
			}
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	public static final <K, V> Multimap<K, V> multimap() {
		return (Multimap<K, V>) MULTIMAP;
	}

	@SuppressWarnings("unchecked")
	public static final <K, V> BiMap<K, V> bimap() {
		return (BiMap<K, V>) BIMAP;
	}

	private Empty() {
	}

}
