package com.robindrew.common.io.data;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.common.base.Supplier;
import com.robindrew.common.io.data.number.DynamicNumber;
import com.robindrew.common.io.data.number.PositiveNumber;

public class DataWriter extends DataOutputStream implements IDataWriter {

	private static final byte NULL = -1;
	private static final byte NOT_NULL = 0;

	private String charsetName = "UTF-8";

	public DataWriter(OutputStream output) {
		super(output);
	}

	public DataWriter(Supplier<OutputStream> supplier) {
		super(supplier.get());
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

	private void writeLength(int length) throws IOException {
		writePositiveInt(length);
	}

	private boolean writeNull(Object value, boolean nullable) throws IOException {
		if (!nullable) {
			if (value == null) {
				throw new NullPointerException("value is null, but not nullable");
			}
			return false;
		}
		if (value == null) {
			writeByte(NULL);
			return true;
		} else {
			writeByte(NOT_NULL);
			return false;
		}
	}

	/**
	 * Write the given (positive) number.
	 * @param value the value.
	 */
	@Override
	public void writePositiveLong(long value) throws IOException {
		PositiveNumber.writeLong(this, value);
	}

	@Override
	public void writePositiveByte(byte value) throws IOException {
		PositiveNumber.writeByte(this, value);
	}

	@Override
	public void writePositiveShort(short value) throws IOException {
		PositiveNumber.writeShort(this, value);
	}

	@Override
	public void writePositiveInt(int value) throws IOException {
		PositiveNumber.writeInt(this, value);
	}

	@Override
	public void writeDynamicByte(byte value) throws IOException {
		DynamicNumber.writeByte(this, value);
	}

	@Override
	public void writeDynamicShort(short value) throws IOException {
		DynamicNumber.writeShort(this, value);
	}

	@Override
	public void writeDynamicInt(int value) throws IOException {
		DynamicNumber.writeInt(this, value);
	}

	@Override
	public void writeDynamicLong(long value) throws IOException {
		DynamicNumber.writeLong(this, value);
	}

	@Override
	public void writeByteObject(Byte value, boolean nullable) throws IOException {
		if (writeNull(value, nullable)) {
			return;
		}
		writeByte(value);
	}

	@Override
	public void writeShortObject(Short value, boolean nullable) throws IOException {
		if (writeNull(value, nullable)) {
			return;
		}
		writeShort(value);
	}

	@Override
	public void writeIntegerObject(Integer value, boolean nullable) throws IOException {
		if (writeNull(value, nullable)) {
			return;
		}
		writeInt(value);
	}

	@Override
	public void writeLongObject(Long value, boolean nullable) throws IOException {
		if (writeNull(value, nullable)) {
			return;
		}
		writeLong(value);
	}

	@Override
	public void writeFloatObject(Float value, boolean nullable) throws IOException {
		if (writeNull(value, nullable)) {
			return;
		}
		writeFloat(value);
	}

	@Override
	public void writeDoubleObject(Double value, boolean nullable) throws IOException {
		if (writeNull(value, nullable)) {
			return;
		}
		writeDouble(value);
	}

	@Override
	public void writeCharacterObject(Character value, boolean nullable) throws IOException {
		if (writeNull(value, nullable)) {
			return;
		}
		writeChar(value);
	}

	@Override
	public void writeBooleanObject(Boolean value, boolean nullable) throws IOException {
		if (!nullable && value == null) {
			throw new NullPointerException("nullable");
		}
		writeByte(value == null ? NULL : (value ? 1 : 0));
	}

	@Override
	public void writeString(String value, boolean nullable) throws IOException {
		if (writeNull(value, nullable)) {
			return;
		}
		byte[] bytes = value.getBytes(charsetName);
		writeLength(bytes.length);
		write(bytes);
	}

	@Override
	public void writeDate(Date value, boolean nullable) throws IOException {
		if (writeNull(value, nullable)) {
			return;
		}
		writeLong(value.getTime());
	}

	@Override
	public void writeByteArray(byte[] array, boolean nullable) throws IOException {
		if (writeNull(array, nullable)) {
			return;
		}
		writeLength(array.length);
		for (byte value : array) {
			writeByte(value);
		}
	}

	@Override
	public void writeShortArray(short[] array, boolean nullable) throws IOException {
		if (writeNull(array, nullable)) {
			return;
		}
		writeLength(array.length);
		for (short value : array) {
			writeShort(value);
		}
	}

	@Override
	public void writeIntArray(int[] array, boolean nullable) throws IOException {
		if (writeNull(array, nullable)) {
			return;
		}
		writeLength(array.length);
		for (int value : array) {
			writeInt(value);
		}
	}

	@Override
	public void writeLongArray(long[] array, boolean nullable) throws IOException {
		if (writeNull(array, nullable)) {
			return;
		}
		writeLength(array.length);
		for (long value : array) {
			writeLong(value);
		}
	}

	@Override
	public void writeFloatArray(float[] array, boolean nullable) throws IOException {
		if (writeNull(array, nullable)) {
			return;
		}
		writeLength(array.length);
		for (float value : array) {
			writeFloat(value);
		}
	}

	@Override
	public void writeDoubleArray(double[] array, boolean nullable) throws IOException {
		if (writeNull(array, nullable)) {
			return;
		}
		writeLength(array.length);
		for (double value : array) {
			writeDouble(value);
		}
	}

	@Override
	public void writeCharArray(char[] array, boolean nullable) throws IOException {
		if (writeNull(array, nullable)) {
			return;
		}
		writeLength(array.length);
		for (char value : array) {
			writeChar(value);
		}
	}

	@Override
	public void writeBooleanArray(boolean[] array, boolean nullableArray) throws IOException {
		if (writeNull(array, nullableArray)) {
			return;
		}
		writeLength(array.length);
		for (boolean value : array) {
			writeBoolean(value);
		}
	}

	@Override
	public void writeByteObjectArray(Byte[] array, boolean nullableArray, boolean nullableElements) throws IOException {
		if (writeNull(array, nullableArray)) {
			return;
		}
		writeLength(array.length);
		for (Byte value : array) {
			writeByteObject(value, nullableElements);
		}
	}

	@Override
	public void writeShortObjectArray(Short[] array, boolean nullableArray, boolean nullableElements) throws IOException {
		if (writeNull(array, nullableArray)) {
			return;
		}
		writeLength(array.length);
		for (Short value : array) {
			writeShortObject(value, nullableElements);
		}
	}

	@Override
	public void writeIntegerObjectArray(Integer[] array, boolean nullableArray, boolean nullableElements) throws IOException {
		if (writeNull(array, nullableArray)) {
			return;
		}
		writeLength(array.length);
		for (Integer value : array) {
			writeIntegerObject(value, nullableElements);
		}
	}

	@Override
	public void writeLongObjectArray(Long[] array, boolean nullableArray, boolean nullableElements) throws IOException {
		if (writeNull(array, nullableArray)) {
			return;
		}
		writeLength(array.length);
		for (Long value : array) {
			writeLongObject(value, nullableElements);
		}
	}

	@Override
	public void writeFloatObjectArray(Float[] array, boolean nullableArray, boolean nullableElements) throws IOException {
		if (writeNull(array, nullableArray)) {
			return;
		}
		writeLength(array.length);
		for (Float value : array) {
			writeFloatObject(value, nullableElements);
		}
	}

	@Override
	public void writeDoubleObjectArray(Double[] array, boolean nullableArray, boolean nullableElements) throws IOException {
		if (writeNull(array, nullableArray)) {
			return;
		}
		writeLength(array.length);
		for (Double value : array) {
			writeDoubleObject(value, nullableElements);
		}
	}

	@Override
	public void writeCharacterObjectArray(Character[] array, boolean nullableArray, boolean nullableElements) throws IOException {
		if (writeNull(array, nullableArray)) {
			return;
		}
		writeLength(array.length);
		for (Character value : array) {
			writeCharacterObject(value, nullableElements);
		}
	}

	@Override
	public void writeBooleanObjectArray(Boolean[] array, boolean nullableArray, boolean nullableElements) throws IOException {
		if (writeNull(array, nullableArray)) {
			return;
		}
		writeLength(array.length);
		for (Boolean value : array) {
			writeBooleanObject(value, nullableElements);
		}
	}

	@Override
	public void writeStringArray(String[] array, boolean nullableArray, boolean nullableElements) throws IOException {
		if (writeNull(array, nullableArray)) {
			return;
		}
		writeLength(array.length);
		for (String value : array) {
			writeString(value, nullableElements);
		}
	}

	@Override
	public void writeDateArray(Date[] array, boolean nullableArray, boolean nullableElements) throws IOException {
		if (writeNull(array, nullableArray)) {
			return;
		}
		writeLength(array.length);
		for (Date value : array) {
			writeDate(value, nullableElements);
		}
	}

	@Override
	public void writeByteObjectList(List<Byte> list, boolean nullableList, boolean nullableElements) throws IOException {
		writeByteObjectCollection(list, nullableList, nullableElements);
	}

	@Override
	public void writeShortObjectList(List<Short> list, boolean nullableList, boolean nullableElements) throws IOException {
		writeShortObjectCollection(list, nullableList, nullableElements);
	}

	@Override
	public void writeIntegerObjectList(List<Integer> list, boolean nullableList, boolean nullableElements) throws IOException {
		writeIntegerObjectCollection(list, nullableList, nullableElements);
	}

	@Override
	public void writeLongObjectList(List<Long> list, boolean nullableList, boolean nullableElements) throws IOException {
		writeLongObjectCollection(list, nullableList, nullableElements);
	}

	@Override
	public void writeFloatObjectList(List<Float> list, boolean nullableList, boolean nullableElements) throws IOException {
		writeFloatObjectCollection(list, nullableList, nullableElements);
	}

	@Override
	public void writeDoubleObjectList(List<Double> list, boolean nullableList, boolean nullableElements) throws IOException {
		writeDoubleObjectCollection(list, nullableList, nullableElements);
	}

	@Override
	public void writeCharacterObjectList(List<Character> list, boolean nullableList, boolean nullableElements) throws IOException {
		writeCharacterObjectCollection(list, nullableList, nullableElements);
	}

	@Override
	public void writeBooleanObjectList(List<Boolean> list, boolean nullableList, boolean nullableElements) throws IOException {
		writeBooleanObjectCollection(list, nullableList, nullableElements);
	}

	@Override
	public void writeStringList(List<String> list, boolean nullableList, boolean nullableElements) throws IOException {
		writeStringCollection(list, nullableList, nullableElements);
	}

	@Override
	public void writeDateList(List<Date> list, boolean nullableList, boolean nullableElements) throws IOException {
		writeDateCollection(list, nullableList, nullableElements);
	}

	@Override
	public void writeByteObjectCollection(Collection<Byte> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		if (writeNull(collection, nullableCollection)) {
			return;
		}
		writeLength(collection.size());
		for (Byte value : collection) {
			writeByteObject(value, nullableElements);
		}
	}

	@Override
	public void writeShortObjectCollection(Collection<Short> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		if (writeNull(collection, nullableCollection)) {
			return;
		}
		writeLength(collection.size());
		for (Short value : collection) {
			writeShortObject(value, nullableElements);
		}
	}

	@Override
	public void writeIntegerObjectCollection(Collection<Integer> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		if (writeNull(collection, nullableCollection)) {
			return;
		}
		writeLength(collection.size());
		for (Integer value : collection) {
			writeIntegerObject(value, nullableElements);
		}
	}

	@Override
	public void writeLongObjectCollection(Collection<Long> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		if (writeNull(collection, nullableCollection)) {
			return;
		}
		writeLength(collection.size());
		for (Long value : collection) {
			writeLongObject(value, nullableElements);
		}
	}

	@Override
	public void writeFloatObjectCollection(Collection<Float> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		if (writeNull(collection, nullableCollection)) {
			return;
		}
		writeLength(collection.size());
		for (Float value : collection) {
			writeFloatObject(value, nullableElements);
		}
	}

	@Override
	public void writeDoubleObjectCollection(Collection<Double> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		if (writeNull(collection, nullableCollection)) {
			return;
		}
		writeLength(collection.size());
		for (Double value : collection) {
			writeDoubleObject(value, nullableElements);
		}
	}

	@Override
	public void writeCharacterObjectCollection(Collection<Character> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		if (writeNull(collection, nullableCollection)) {
			return;
		}
		writeLength(collection.size());
		for (Character value : collection) {
			writeCharacterObject(value, nullableElements);
		}
	}

	@Override
	public void writeBooleanObjectCollection(Collection<Boolean> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		if (writeNull(collection, nullableCollection)) {
			return;
		}
		writeLength(collection.size());
		for (Boolean value : collection) {
			writeBooleanObject(value, nullableElements);
		}
	}

	@Override
	public void writeStringCollection(Collection<String> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		if (writeNull(collection, nullableCollection)) {
			return;
		}
		writeLength(collection.size());
		for (String value : collection) {
			writeString(value, nullableElements);
		}
	}

	@Override
	public void writeDateCollection(Collection<Date> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		if (writeNull(collection, nullableCollection)) {
			return;
		}
		writeLength(collection.size());
		for (Date value : collection) {
			writeDate(value, nullableElements);
		}
	}

	@Override
	public <E extends Enum<E>> void writeEnum(E value, boolean nullable) throws IOException {
		if (writeNull(value, nullable)) {
			return;
		}
		int ordinal = value.ordinal();
		if (ordinal > Short.MAX_VALUE) {
			throw new IllegalArgumentException("ordinal=" + ordinal + " for enum '" + value + "' of type: " + value.getClass());
		}
		writePositiveInt(ordinal);
	}

	@Override
	public <T> void writeObjectCollection(Collection<T> collection, boolean nullableCollection, boolean nullableElements, IDataSerializer<T> serializer) throws IOException {
		if (writeNull(collection, nullableCollection)) {
			return;
		}
		writePositiveInt(collection.size());
		for (T element : collection) {
			if (!writeNull(element, nullableElements)) {
				serializer.writeObject(this, element);
			}
		}
	}

	@Override
	public <T> void writeObject(T object, boolean nullable, IDataSerializer<T> serializer) throws IOException {
		if (writeNull(object, nullable)) {
			return;
		}
		serializer.writeObject(this, object);
	}

	@Override
	public <E extends Enum<E>> void writeEnumArray(E[] array, boolean nullableArray, boolean nullableElements) throws IOException {
		if (writeNull(array, nullableArray)) {
			return;
		}
		writeLength(array.length);
		for (E value : array) {
			writeEnum(value, nullableElements);
		}
	}

	@Override
	public <E extends Enum<E>> void writeEnumList(List<E> list, boolean nullableList, boolean nullableElements) throws IOException {
		writeEnumCollection(list, nullableList, nullableElements);
	}

	@Override
	public <E extends Enum<E>> void writeEnumCollection(Collection<E> collection, boolean nullableCollection, boolean nullableElements) throws IOException {
		if (writeNull(collection, nullableCollection)) {
			return;
		}
		writeLength(collection.size());
		for (E value : collection) {
			writeEnum(value, nullableElements);
		}
	}

}
