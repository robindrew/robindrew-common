package com.robindrew.common.io.data;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.robindrew.common.io.data.number.DynamicNumber;
import com.robindrew.common.io.data.number.PositiveNumber;

public class DataReader extends DataInputStream implements IDataReader {

	private static final byte NULL = -1;

	private String charsetName = "UTF-8";

	public DataReader(InputStream input) {
		super(input);
	}

	public DataReader(byte[] bytes) {
		super(new ByteArrayInputStream(bytes));
	}

	@Override
	public String getCharset() {
		return charsetName;
	}

	@Override
	public void setCharset(String charsetName) {
		if (charsetName.length() == 0) {
			throw new IllegalArgumentException("charsetName is empty");
		}
		this.charsetName = charsetName;
	}

	private boolean isNull() throws IOException {
		return readByte() == NULL;
	}

	private int readLength(boolean nullable) throws IOException {
		if (nullable) {
			if (isNull()) {
				return NULL;
			}
		}
		return readPositiveInt();
	}

	@Override
	public long readPositiveLong() throws IOException {
		return PositiveNumber.readLong(this);
	}

	@Override
	public int readPositiveInt() throws IOException {
		return PositiveNumber.readInt(this);
	}

	@Override
	public short readPositiveShort() throws IOException {
		return PositiveNumber.readShort(this);
	}

	@Override
	public byte readPositiveByte() throws IOException {
		return PositiveNumber.readByte(this);
	}

	@Override
	public long readDynamicLong() throws IOException {
		return DynamicNumber.readLong(this);
	}

	@Override
	public int readDynamicInt() throws IOException {
		return DynamicNumber.readInt(this);
	}

	@Override
	public short readDynamicShort() throws IOException {
		return DynamicNumber.readShort(this);
	}

	@Override
	public byte readDynamicByte() throws IOException {
		return DynamicNumber.readByte(this);
	}

	@Override
	public Byte readByteObject(boolean nullable) throws IOException {
		if (nullable && isNull()) {
			return null;
		}
		return readByte();
	}

	@Override
	public Short readShortObject(boolean nullable) throws IOException {
		if (nullable && isNull()) {
			return null;
		}
		return readShort();
	}

	@Override
	public Integer readIntegerObject(boolean nullable) throws IOException {
		if (nullable && isNull()) {
			return null;
		}
		return readInt();
	}

	@Override
	public Long readLongObject(boolean nullable) throws IOException {
		if (nullable && isNull()) {
			return null;
		}
		return readLong();
	}

	@Override
	public Float readFloatObject(boolean nullable) throws IOException {
		if (nullable && isNull()) {
			return null;
		}
		return readFloat();
	}

	@Override
	public Double readDoubleObject(boolean nullable) throws IOException {
		if (nullable && isNull()) {
			return null;
		}
		return readDouble();
	}

	@Override
	public Character readCharacterObject(boolean nullable) throws IOException {
		if (nullable && isNull()) {
			return null;
		}
		return readChar();
	}

	@Override
	public Boolean readBooleanObject(boolean nullable) throws IOException {
		byte value = readByte();
		if (value == NULL) {
			if (!nullable) {
				throw new IllegalStateException("value is not null");
			}
			return null;
		}
		return (value > 0 ? Boolean.TRUE : Boolean.FALSE);
	}

	@Override
	public String readString(boolean nullable) throws IOException {
		int length = readLength(nullable);
		if (length == NULL) {
			return null;
		}
		byte[] bytes = new byte[length];
		readFully(bytes);
		return new String(bytes, charsetName);
	}

	@Override
	public Date readDate(boolean nullable) throws IOException {
		if (nullable && isNull()) {
			return null;
		}
		return new Date(readLong());
	}

	@Override
	public byte[] readByteArray(boolean nullable) throws IOException {
		int length = readLength(nullable);
		if (length == NULL) {
			return null;
		}
		byte[] array = new byte[length];
		for (int i = 0; i < length; i++) {
			array[i] = readByte();
		}
		return array;
	}

	@Override
	public short[] readShortArray(boolean nullable) throws IOException {
		int length = readLength(nullable);
		if (length == NULL) {
			return null;
		}
		short[] array = new short[length];
		for (int i = 0; i < length; i++) {
			array[i] = readShort();
		}
		return array;
	}

	@Override
	public int[] readIntArray(boolean nullable) throws IOException {
		int length = readLength(nullable);
		if (length == NULL) {
			return null;
		}
		int[] array = new int[length];
		for (int i = 0; i < length; i++) {
			array[i] = readInt();
		}
		return array;
	}

	@Override
	public long[] readLongArray(boolean nullable) throws IOException {
		int length = readLength(nullable);
		if (length == NULL) {
			return null;
		}
		long[] array = new long[length];
		for (int i = 0; i < length; i++) {
			array[i] = readLong();
		}
		return array;
	}

	@Override
	public float[] readFloatArray(boolean nullable) throws IOException {
		int length = readLength(nullable);
		if (length == NULL) {
			return null;
		}
		float[] array = new float[length];
		for (int i = 0; i < length; i++) {
			array[i] = readFloat();
		}
		return array;
	}

	@Override
	public double[] readDoubleArray(boolean nullable) throws IOException {
		int length = readLength(nullable);
		if (length == NULL) {
			return null;
		}
		double[] array = new double[length];
		for (int i = 0; i < length; i++) {
			array[i] = readDouble();
		}
		return array;
	}

	@Override
	public char[] readCharArray(boolean nullable) throws IOException {
		int length = readLength(nullable);
		if (length == NULL) {
			return null;
		}
		char[] array = new char[length];
		for (int i = 0; i < length; i++) {
			array[i] = readChar();
		}
		return array;
	}

	@Override
	public boolean[] readBooleanArray(boolean nullable) throws IOException {
		int length = readLength(nullable);
		if (length == NULL) {
			return null;
		}
		boolean[] array = new boolean[length];
		for (int i = 0; i < length; i++) {
			array[i] = readBoolean();
		}
		return array;
	}

	@Override
	public Byte[] readByteObjectArray(boolean nullableArray, boolean nullableElements) throws IOException {
		int length = readLength(nullableArray);
		if (length == NULL) {
			return null;
		}
		Byte[] array = new Byte[length];
		for (int i = 0; i < length; i++) {
			array[i] = readByteObject(nullableElements);
		}
		return array;
	}

	@Override
	public Short[] readShortObjectArray(boolean nullableArray, boolean nullableElements) throws IOException {
		int length = readLength(nullableArray);
		if (length == NULL) {
			return null;
		}
		Short[] array = new Short[length];
		for (int i = 0; i < length; i++) {
			array[i] = readShortObject(nullableElements);
		}
		return array;
	}

	@Override
	public Integer[] readIntegerObjectArray(boolean nullableArray, boolean nullableElements) throws IOException {
		int length = readLength(nullableArray);
		if (length == NULL) {
			return null;
		}
		Integer[] array = new Integer[length];
		for (int i = 0; i < length; i++) {
			array[i] = readIntegerObject(nullableElements);
		}
		return array;
	}

	@Override
	public Long[] readLongObjectArray(boolean nullableArray, boolean nullableElements) throws IOException {
		int length = readLength(nullableArray);
		if (length == NULL) {
			return null;
		}
		Long[] array = new Long[length];
		for (int i = 0; i < length; i++) {
			array[i] = readLongObject(nullableElements);
		}
		return array;
	}

	@Override
	public Float[] readFloatObjectArray(boolean nullableArray, boolean nullableElements) throws IOException {
		int length = readLength(nullableArray);
		if (length == NULL) {
			return null;
		}
		Float[] array = new Float[length];
		for (int i = 0; i < length; i++) {
			array[i] = readFloatObject(nullableElements);
		}
		return array;
	}

	@Override
	public Double[] readDoubleObjectArray(boolean nullableArray, boolean nullableElements) throws IOException {
		int length = readLength(nullableArray);
		if (length == NULL) {
			return null;
		}
		Double[] array = new Double[length];
		for (int i = 0; i < length; i++) {
			array[i] = readDoubleObject(nullableElements);
		}
		return array;
	}

	@Override
	public Character[] readCharacterObjectArray(boolean nullableArray, boolean nullableElements) throws IOException {
		int length = readLength(nullableArray);
		if (length == NULL) {
			return null;
		}
		Character[] array = new Character[length];
		for (int i = 0; i < length; i++) {
			array[i] = readCharacterObject(nullableElements);
		}
		return array;
	}

	@Override
	public Boolean[] readBooleanObjectArray(boolean nullableArray, boolean nullableElements) throws IOException {
		int length = readLength(nullableArray);
		if (length == NULL) {
			return null;
		}
		Boolean[] array = new Boolean[length];
		for (int i = 0; i < length; i++) {
			array[i] = readBooleanObject(nullableElements);
		}
		return array;
	}

	@Override
	public String[] readStringArray(boolean nullableArray, boolean nullableElements) throws IOException {
		int length = readLength(nullableArray);
		if (length == NULL) {
			return null;
		}
		String[] array = new String[length];
		for (int i = 0; i < length; i++) {
			array[i] = readString(nullableElements);
		}
		return array;
	}

	@Override
	public Date[] readDateArray(boolean nullableArray, boolean nullableElements) throws IOException {
		int length = readLength(nullableArray);
		if (length == NULL) {
			return null;
		}
		Date[] array = new Date[length];
		for (int i = 0; i < length; i++) {
			array[i] = readDate(nullableElements);
		}
		return array;
	}

	@Override
	public List<Byte> readByteObjectList(boolean nullableList, boolean nullableElements) throws IOException {
		int length = readLength(nullableList);
		if (length == NULL) {
			return null;
		}
		List<Byte> list = new ArrayList<Byte>(length);
		for (int i = 0; i < length; i++) {
			Byte element = readByteObject(nullableElements);
			list.add(element);
		}
		return list;
	}

	@Override
	public List<Short> readShortObjectList(boolean nullableList, boolean nullableElements) throws IOException {
		int length = readLength(nullableList);
		if (length == NULL) {
			return null;
		}
		List<Short> list = new ArrayList<Short>(length);
		for (int i = 0; i < length; i++) {
			Short element = readShortObject(nullableElements);
			list.add(element);
		}
		return list;
	}

	@Override
	public List<Integer> readIntegerObjectList(boolean nullableList, boolean nullableElements) throws IOException {
		int length = readLength(nullableList);
		if (length == NULL) {
			return null;
		}
		List<Integer> list = new ArrayList<Integer>(length);
		for (int i = 0; i < length; i++) {
			Integer element = readIntegerObject(nullableElements);
			list.add(element);
		}
		return list;
	}

	@Override
	public List<Long> readLongObjectList(boolean nullableList, boolean nullableElements) throws IOException {
		int length = readLength(nullableList);
		if (length == NULL) {
			return null;
		}
		List<Long> list = new ArrayList<Long>(length);
		for (int i = 0; i < length; i++) {
			Long element = readLongObject(nullableElements);
			list.add(element);
		}
		return list;
	}

	@Override
	public List<Float> readFloatObjectList(boolean nullableList, boolean nullableElements) throws IOException {
		int length = readLength(nullableList);
		if (length == NULL) {
			return null;
		}
		List<Float> list = new ArrayList<Float>(length);
		for (int i = 0; i < length; i++) {
			Float element = readFloatObject(nullableElements);
			list.add(element);
		}
		return list;
	}

	@Override
	public List<Double> readDoubleObjectList(boolean nullableList, boolean nullableElements) throws IOException {
		int length = readLength(nullableList);
		if (length == NULL) {
			return null;
		}
		List<Double> list = new ArrayList<Double>(length);
		for (int i = 0; i < length; i++) {
			Double element = readDoubleObject(nullableElements);
			list.add(element);
		}
		return list;
	}

	@Override
	public List<Character> readCharacterObjectList(boolean nullableList, boolean nullableElements) throws IOException {
		int length = readLength(nullableList);
		if (length == NULL) {
			return null;
		}
		List<Character> list = new ArrayList<Character>(length);
		for (int i = 0; i < length; i++) {
			Character element = readCharacterObject(nullableElements);
			list.add(element);
		}
		return list;
	}

	@Override
	public List<Boolean> readBooleanObjectList(boolean nullableList, boolean nullableElements) throws IOException {
		int length = readLength(nullableList);
		if (length == NULL) {
			return null;
		}
		List<Boolean> list = new ArrayList<Boolean>(length);
		for (int i = 0; i < length; i++) {
			Boolean element = readBooleanObject(nullableElements);
			list.add(element);
		}
		return list;
	}

	@Override
	public List<String> readStringList(boolean nullableList, boolean nullableElements) throws IOException {
		int length = readLength(nullableList);
		if (length == NULL) {
			return null;
		}
		List<String> list = new ArrayList<String>(length);
		for (int i = 0; i < length; i++) {
			String element = readString(nullableElements);
			list.add(element);
		}
		return list;
	}

	@Override
	public List<Date> readDateList(boolean nullableList, boolean nullableElements) throws IOException {
		int length = readLength(nullableList);
		if (length == NULL) {
			return null;
		}
		List<Date> list = new ArrayList<Date>(length);
		for (int i = 0; i < length; i++) {
			Date element = readDate(nullableElements);
			list.add(element);
		}
		return list;
	}

	@Override
	public Collection<Byte> readByteObjectCollection(Collection<Byte> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		int length = readLength(nullableCollection);
		if (length == NULL) {
			return null;
		}
		for (int i = 0; i < length; i++) {
			Byte element = readByteObject(nullableElements);
			collection.add(element);
		}
		return collection;
	}

	@Override
	public Collection<Short> readShortCollection(Collection<Short> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		int length = readLength(nullableCollection);
		if (length == NULL) {
			return null;
		}
		for (int i = 0; i < length; i++) {
			Short element = readShortObject(nullableElements);
			collection.add(element);
		}
		return collection;
	}

	@Override
	public Collection<Integer> readIntegerCollection(Collection<Integer> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		int length = readLength(nullableCollection);
		if (length == NULL) {
			return null;
		}
		for (int i = 0; i < length; i++) {
			Integer element = readIntegerObject(nullableElements);
			collection.add(element);
		}
		return collection;
	}

	@Override
	public Collection<Long> readLongCollection(Collection<Long> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		int length = readLength(nullableCollection);
		if (length == NULL) {
			return null;
		}
		for (int i = 0; i < length; i++) {
			Long element = readLongObject(nullableElements);
			collection.add(element);
		}
		return collection;
	}

	@Override
	public Collection<Float> readFloatCollection(Collection<Float> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		int length = readLength(nullableCollection);
		if (length == NULL) {
			return null;
		}
		for (int i = 0; i < length; i++) {
			Float element = readFloatObject(nullableElements);
			collection.add(element);
		}
		return collection;
	}

	@Override
	public Collection<Double> readDoubleCollection(Collection<Double> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		int length = readLength(nullableCollection);
		if (length == NULL) {
			return null;
		}
		for (int i = 0; i < length; i++) {
			Double element = readDoubleObject(nullableElements);
			collection.add(element);
		}
		return collection;
	}

	@Override
	public Collection<Character> readCharacterCollection(Collection<Character> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		int length = readLength(nullableCollection);
		if (length == NULL) {
			return null;
		}
		for (int i = 0; i < length; i++) {
			Character element = readCharacterObject(nullableElements);
			collection.add(element);
		}
		return collection;
	}

	@Override
	public Collection<Boolean> readBooleanCollection(Collection<Boolean> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		int length = readLength(nullableCollection);
		if (length == NULL) {
			return null;
		}
		for (int i = 0; i < length; i++) {
			Boolean element = readBooleanObject(nullableElements);
			collection.add(element);
		}
		return collection;
	}

	@Override
	public Collection<String> readStringCollection(Collection<String> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		int length = readLength(nullableCollection);
		if (length == NULL) {
			return null;
		}
		for (int i = 0; i < length; i++) {
			String element = readString(nullableElements);
			collection.add(element);
		}
		return collection;
	}

	@Override
	public Collection<Date> readDateCollection(Collection<Date> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		int length = readLength(nullableCollection);
		if (length == NULL) {
			return null;
		}
		for (int i = 0; i < length; i++) {
			Date element = readDate(nullableElements);
			collection.add(element);
		}
		return collection;
	}

	@Override
	public <E extends Enum<E>> E readEnum(Class<E> enumType, boolean nullable) throws IOException {
		if (nullable && isNull()) {
			return null;
		}
		int ordinal = readPositiveInt();
		E[] constants = enumType.getEnumConstants();
		return constants[ordinal];
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, C extends Collection<T>> C readObjectCollection(C collection, boolean nullableCollection, boolean nullableElements, IDataSerializer<T> serializer) throws IOException {
		int length = readLength(nullableCollection);
		if (length == NULL) {
			return null;
		}
		if (collection == null) {
			collection = (C) new ArrayList<T>(length);
		}
		for (int i = 0; i < length; i++) {
			T element = null;
			if (!nullableElements || !isNull()) {
				element = serializer.readObject(this);
			}
			collection.add(element);
		}
		return collection;
	}

	@Override
	public <T> T readObject(boolean nullable, IDataSerializer<T> serializer) throws IOException {
		if (nullable && isNull()) {
			return null;
		}
		return serializer.readObject(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Enum<E>> E[] readEnumArray(Class<E> enumType, boolean nullableArray, boolean nullableElements) throws IOException {
		int length = readLength(nullableArray);
		if (length == NULL) {
			return null;
		}
		E[] array = (E[]) Array.newInstance(enumType, length);
		for (int i = 0; i < length; i++) {
			array[i] = readEnum(enumType, nullableElements);
		}
		return array;
	}

	@Override
	public <E extends Enum<E>> List<E> readEnumList(Class<E> enumType, boolean nullableList, boolean nullableElements) throws IOException {
		int length = readLength(nullableList);
		if (length == NULL) {
			return null;
		}
		List<E> list = Lists.newArrayList();
		for (int i = 0; i < length; i++) {
			E element = readEnum(enumType, nullableElements);
			list.add(element);
		}
		return list;
	}

	@Override
	public <E extends Enum<E>> Collection<E> readEnumCollection(Class<E> enumType, Collection<E> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		int length = readLength(nullableCollection);
		if (length == NULL) {
			return null;
		}
		for (int i = 0; i < length; i++) {
			E element = readEnum(enumType, nullableElements);
			collection.add(element);
		}
		return collection;
	}
}
