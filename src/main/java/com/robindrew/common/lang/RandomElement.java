package com.robindrew.common.lang;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A Random Element.
 * <p>
 * on {@link Random} with a number of useful utility methods.
 */
public class RandomElement extends Random {

	/** The serialization id. */
	private static final long serialVersionUID = -5932783012742419135L;

	public RandomElement() {
	}

	public RandomElement(long seed) {
		super(seed);
	}

	public <E> E nextElement(List<E> list, boolean remove) {
		final int size = list.size();
		if (size == 0) {
			throw new IllegalArgumentException("list is empty");
		}
		if (size == 1) {
			return list.get(0);
		}
		int index = nextInt(size);
		return remove ? list.remove(index) : list.get(index);
	}

	public <E> E nextElement(List<E> list) {
		return nextElement(list, false);
	}

	public <E> E nextElement(E[] array) {
		final int length = array.length;
		if (length == 0) {
			throw new IllegalArgumentException("array is empty");
		}
		if (length == 1) {
			return array[0];
		}
		int index = nextInt(length);
		return array[index];
	}

	public <E> E nextElement(Collection<E> collection, boolean remove) {
		final int size = collection.size();
		if (size == 0) {
			throw new IllegalArgumentException("list is empty");
		}
		int index = nextInt(size);
		Iterator<E> iterator = collection.iterator();
		while (iterator.hasNext()) {
			E element = iterator.next();
			if (index == 0) {
				if (remove) {
					iterator.remove();
				}
				return element;
			}
			index--;
		}
		throw new IllegalStateException("");
	}

	public <E> E nextElement(Collection<E> collection) {
		return nextElement(collection, false);
	}

	public <E extends Enum<E>> E nextEnum(Class<E> enumClass) {
		E[] constants = enumClass.getEnumConstants();
		return nextElement(constants);
	}

	public byte nextByte() {
		return (byte) nextInt();
	}

	public short nextShort() {
		return (short) nextInt();
	}

	public char nextChar() {
		return (char) nextInt();
	}

	public int nextInt(int min, int max) {
		return (int) nextLong(min, max);
	}

	public Byte nextByteObject(boolean nullable) {
		if (nullable && nextBoolean()) {
			return null;
		}
		return nextByte();
	}

	public Short nextShortObject(boolean nullable) {
		if (nullable && nextBoolean()) {
			return null;
		}
		return nextShort();
	}

	public Integer nextIntegerObject(boolean nullable) {
		if (nullable && nextBoolean()) {
			return null;
		}
		return nextInt();
	}

	public Long nextLongObject(boolean nullable) {
		if (nullable && nextBoolean()) {
			return null;
		}
		return nextLong();
	}

	public Float nextFloatObject(boolean nullable) {
		if (nullable && nextBoolean()) {
			return null;
		}
		return nextFloat();
	}

	public Double nextDoubleObject(boolean nullable) {
		if (nullable && nextBoolean()) {
			return null;
		}
		return nextDouble();
	}

	public Boolean nextBooleanObject(boolean nullable) {
		if (nullable && nextBoolean()) {
			return null;
		}
		return nextBoolean();
	}

	public Character nextCharacterObject(boolean nullable) {
		if (nullable && nextBoolean()) {
			return null;
		}
		return nextChar();
	}

	public String nextString(int length, boolean nullable) {
		if (nullable && nextBoolean()) {
			return null;
		}
		char[] array = nextCharArray(length, false);
		return new String(array);
	}

	public Date nextDate(boolean nullable) {
		if (nullable && nextBoolean()) {
			return null;
		}
		return new Date(nextLong());
	}

	public byte[] nextByteArray(int length, boolean nullableArray) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		byte[] array = new byte[length];
		nextBytes(array);
		return array;
	}

	public short[] nextShortArray(int length, boolean nullableArray) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		short[] array = new short[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextShort();
		}
		return array;
	}

	public int[] nextIntArray(int length, boolean nullableArray) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		int[] array = new int[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextInt();
		}
		return array;
	}

	public long[] nextLongArray(int length, boolean nullableArray) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		long[] array = new long[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextLong();
		}
		return array;
	}

	public char[] nextCharArray(int length, boolean nullableArray) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		char[] array = new char[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextChar();
		}
		return array;
	}

	public boolean[] nextBooleanArray(int length, boolean nullableArray) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		boolean[] array = new boolean[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextBoolean();
		}
		return array;
	}

	public float[] nextFloatArray(int length, boolean nullableArray) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		float[] array = new float[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextFloat();
		}
		return array;
	}

	public double[] nextDoubleArray(int length, boolean nullableArray) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		double[] array = new double[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextDouble();
		}
		return array;
	}

	public Byte[] nextByteObjectArray(int length, boolean nullableArray, boolean nullableElements) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		Byte[] array = new Byte[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextByteObject(nullableElements);
		}
		return array;
	}

	public Short[] nextShortObjectArray(int length, boolean nullableArray, boolean nullableElements) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		Short[] array = new Short[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextShortObject(nullableElements);
		}
		return array;
	}

	public Integer[] nextIntegerObjectArray(int length, boolean nullableArray, boolean nullableElements) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		Integer[] array = new Integer[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextIntegerObject(nullableElements);
		}
		return array;
	}

	public Long[] nextLongObjectArray(int length, boolean nullableArray, boolean nullableElements) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		Long[] array = new Long[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextLongObject(nullableElements);
		}
		return array;
	}

	public Float[] nextFloatObjectArray(int length, boolean nullableArray, boolean nullableElements) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		Float[] array = new Float[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextFloatObject(nullableElements);
		}
		return array;
	}

	public Double[] nextDoubleObjectArray(int length, boolean nullableArray, boolean nullableElements) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		Double[] array = new Double[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextDoubleObject(nullableElements);
		}
		return array;
	}

	public Character[] nextCharacterObjectArray(int length, boolean nullableArray, boolean nullableElements) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		Character[] array = new Character[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextCharacterObject(nullableElements);
		}
		return array;
	}

	public Boolean[] nextBooleanObjectArray(int length, boolean nullableArray, boolean nullableElements) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		Boolean[] array = new Boolean[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextBooleanObject(nullableElements);
		}
		return array;
	}

	public String[] nextStringObjectArray(int length, int elementLength, boolean nullableArray, boolean nullableElements) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		String[] array = new String[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextString(elementLength, nullableElements);
		}
		return array;
	}

	public Date[] nextDateObjectArray(int length, boolean nullableArray, boolean nullableElements) {
		if (nullableArray && nextBoolean()) {
			return null;
		}
		Date[] array = new Date[length];
		for (int i = 0; i < length; i++) {
			array[i] = nextDate(nullableElements);
		}
		return array;
	}

	public long nextLong(long min, long max) {
		if (min == max) {
			return min;
		}
		if (min > max) {
			throw new IllegalArgumentException("min=" + min + ", max=" + max);
		}
		long value = (max - min) + 1;
		long random = nextLong();
		if (random < 0) {
			random = -random;
		}
		return (random % value) + min;
	}
}
