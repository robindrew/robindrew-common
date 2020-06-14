package com.robindrew.common.io.data;

import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * The Data Set Input interface provides advanced stream reading capabilities.
 */
public interface IDataWriter extends DataOutput, AutoCloseable {

	@Override
	void close() throws IOException;

	void flush() throws IOException;

	Charset getCharset();

	void setCharset(Charset charset);

	void writeNull(boolean isNull) throws IOException;

	void writePositiveByte(byte value) throws IOException;

	void writePositiveShort(short value) throws IOException;

	void writePositiveInt(int value) throws IOException;

	void writePositiveLong(long value) throws IOException;

	void writeDynamicByte(byte value) throws IOException;

	void writeDynamicShort(short value) throws IOException;

	void writeDynamicInt(int value) throws IOException;

	void writeDynamicLong(long value) throws IOException;

	<E extends Enum<E>> void writeEnum(E value, boolean nullable) throws IOException;

	void writeByteObject(Byte value, boolean nullable) throws IOException;

	void writeShortObject(Short value, boolean nullable) throws IOException;

	void writeIntegerObject(Integer value, boolean nullable) throws IOException;

	void writeLongObject(Long value, boolean nullable) throws IOException;

	void writeFloatObject(Float value, boolean nullable) throws IOException;

	void writeDoubleObject(Double value, boolean nullable) throws IOException;

	void writeCharacterObject(Character value, boolean nullable) throws IOException;

	void writeBooleanObject(Boolean value, boolean nullable) throws IOException;

	void writeString(String value, boolean nullable) throws IOException;

	void writeString(String value, boolean nullable, Charset charset) throws IOException;

	void writeDate(Date value, boolean nullable) throws IOException;

	void writeByteArray(byte[] array, boolean nullable) throws IOException;

	void writeShortArray(short[] array, boolean nullable) throws IOException;

	void writeIntArray(int[] array, boolean nullable) throws IOException;

	void writeLongArray(long[] array, boolean nullable) throws IOException;

	void writeFloatArray(float[] array, boolean nullable) throws IOException;

	void writeDoubleArray(double[] array, boolean nullable) throws IOException;

	void writeCharArray(char[] array, boolean nullable) throws IOException;

	void writeBooleanArray(boolean[] array, boolean nullable) throws IOException;

	void writeByteObjectArray(Byte[] array, boolean nullableArray, boolean nullableElements) throws IOException;

	void writeShortObjectArray(Short[] array, boolean nullableArray, boolean nullableElements) throws IOException;

	void writeIntegerObjectArray(Integer[] array, boolean nullableArray, boolean nullableElements) throws IOException;

	void writeLongObjectArray(Long[] array, boolean nullableArray, boolean nullableElements) throws IOException;

	void writeFloatObjectArray(Float[] array, boolean nullableArray, boolean nullableElements) throws IOException;

	void writeDoubleObjectArray(Double[] array, boolean nullableArray, boolean nullableElements) throws IOException;

	void writeCharacterObjectArray(Character[] array, boolean nullableArray, boolean nullableElements) throws IOException;

	void writeBooleanObjectArray(Boolean[] array, boolean nullableArray, boolean nullableElements) throws IOException;

	void writeStringArray(String[] array, boolean nullableArray, boolean nullableElements) throws IOException;

	void writeDateArray(Date[] array, boolean nullableArray, boolean nullableElements) throws IOException;

	<E extends Enum<E>> void writeEnumArray(E[] array, boolean nullableArray, boolean nullableElements) throws IOException;

	void writeByteObjectList(List<Byte> list, boolean nullableList, boolean nullableElements) throws IOException;

	void writeShortObjectList(List<Short> list, boolean nullableList, boolean nullableElements) throws IOException;

	void writeIntegerObjectList(List<Integer> list, boolean nullableList, boolean nullableElements) throws IOException;

	void writeLongObjectList(List<Long> list, boolean nullableList, boolean nullableElements) throws IOException;

	void writeFloatObjectList(List<Float> list, boolean nullableList, boolean nullableElements) throws IOException;

	void writeDoubleObjectList(List<Double> list, boolean nullableList, boolean nullableElements) throws IOException;

	void writeCharacterObjectList(List<Character> list, boolean nullableList, boolean nullableElements) throws IOException;

	void writeBooleanObjectList(List<Boolean> list, boolean nullableList, boolean nullableElements) throws IOException;

	void writeStringList(List<String> list, boolean nullableList, boolean nullableElements) throws IOException;

	void writeDateList(List<Date> list, boolean nullableList, boolean nullableElements) throws IOException;

	<E extends Enum<E>> void writeEnumList(List<E> list, boolean nullableList, boolean nullableElements) throws IOException;

	void writeByteObjectCollection(Collection<Byte> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	void writeShortObjectCollection(Collection<Short> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	void writeIntegerObjectCollection(Collection<Integer> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	void writeLongObjectCollection(Collection<Long> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	void writeFloatObjectCollection(Collection<Float> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	void writeDoubleObjectCollection(Collection<Double> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	void writeCharacterObjectCollection(Collection<Character> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	void writeBooleanObjectCollection(Collection<Boolean> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	void writeStringCollection(Collection<String> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	void writeDateCollection(Collection<Date> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	<E extends Enum<E>> void writeEnumCollection(Collection<E> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	<T> void writeObjectCollection(Collection<T> collection, boolean nullableCollection, boolean nullableElements, IDataSerializer<T> serializer) throws IOException;

	<T> void writeObject(T object, boolean nullable, IDataSerializer<T> serializer) throws IOException;

	<T> void writeObject(T object, IDataSerializer<T> serializer) throws IOException;

}
