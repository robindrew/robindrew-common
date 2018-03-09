package com.robindrew.common.io.data;

import java.io.DataInput;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * The Data Set Input interface provides advanced stream reading capabilities.
 */
public interface IDataReader extends DataInput, AutoCloseable {

	void close() throws IOException;

	String getCharset();

	void setCharset(String charsetName);

	byte readPositiveByte() throws IOException;

	short readPositiveShort() throws IOException;

	int readPositiveInt() throws IOException;

	long readPositiveLong() throws IOException;

	byte readDynamicByte() throws IOException;

	short readDynamicShort() throws IOException;

	int readDynamicInt() throws IOException;

	long readDynamicLong() throws IOException;

	<E extends Enum<E>> E readEnum(Class<E> enumType, boolean nullable) throws IOException;

	Byte readByteObject(boolean nullable) throws IOException;

	Short readShortObject(boolean nullable) throws IOException;

	Integer readIntegerObject(boolean nullable) throws IOException;

	Long readLongObject(boolean nullable) throws IOException;

	Float readFloatObject(boolean nullable) throws IOException;

	Double readDoubleObject(boolean nullable) throws IOException;

	Character readCharacterObject(boolean nullable) throws IOException;

	Boolean readBooleanObject(boolean nullable) throws IOException;

	String readString(boolean nullable) throws IOException;

	Date readDate(boolean nullable) throws IOException;

	byte[] readByteArray(boolean nullable) throws IOException;

	short[] readShortArray(boolean nullable) throws IOException;

	int[] readIntArray(boolean nullable) throws IOException;

	long[] readLongArray(boolean nullable) throws IOException;

	float[] readFloatArray(boolean nullable) throws IOException;

	double[] readDoubleArray(boolean nullable) throws IOException;

	char[] readCharArray(boolean nullable) throws IOException;

	boolean[] readBooleanArray(boolean nullable) throws IOException;

	Byte[] readByteObjectArray(boolean nullableArray, boolean nullableElements) throws IOException;

	Short[] readShortObjectArray(boolean nullableArray, boolean nullableElements) throws IOException;

	Integer[] readIntegerObjectArray(boolean nullableArray, boolean nullableElements) throws IOException;

	Long[] readLongObjectArray(boolean nullableArray, boolean nullableElements) throws IOException;

	Float[] readFloatObjectArray(boolean nullableArray, boolean nullableElements) throws IOException;

	Double[] readDoubleObjectArray(boolean nullableArray, boolean nullableElements) throws IOException;

	Character[] readCharacterObjectArray(boolean nullableArray, boolean nullableElements) throws IOException;

	Boolean[] readBooleanObjectArray(boolean nullableArray, boolean nullableElements) throws IOException;

	String[] readStringArray(boolean nullableArray, boolean nullableElements) throws IOException;

	Date[] readDateArray(boolean nullableArray, boolean nullableElements) throws IOException;

	<E extends Enum<E>> E[] readEnumArray(Class<E> enumType, boolean nullableArray, boolean nullableElements) throws IOException;

	List<Byte> readByteObjectList(boolean nullableList, boolean nullableElements) throws IOException;

	List<Short> readShortObjectList(boolean nullableList, boolean nullableElements) throws IOException;

	List<Integer> readIntegerObjectList(boolean nullableList, boolean nullableElements) throws IOException;

	List<Long> readLongObjectList(boolean nullableList, boolean nullableElements) throws IOException;

	List<Float> readFloatObjectList(boolean nullableList, boolean nullableElements) throws IOException;

	List<Double> readDoubleObjectList(boolean nullableList, boolean nullableElements) throws IOException;

	List<Character> readCharacterObjectList(boolean nullableList, boolean nullableElements) throws IOException;

	List<Boolean> readBooleanObjectList(boolean nullableList, boolean nullableElements) throws IOException;

	List<String> readStringList(boolean nullableList, boolean nullableElements) throws IOException;

	List<Date> readDateList(boolean nullableList, boolean nullableElements) throws IOException;

	<E extends Enum<E>> List<E> readEnumList(Class<E> enumType, boolean nullableList, boolean nullableElements) throws IOException;

	Collection<Byte> readByteObjectCollection(Collection<Byte> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	Collection<Short> readShortCollection(Collection<Short> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	Collection<Integer> readIntegerCollection(Collection<Integer> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	Collection<Long> readLongCollection(Collection<Long> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	Collection<Float> readFloatCollection(Collection<Float> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	Collection<Double> readDoubleCollection(Collection<Double> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	Collection<Character> readCharacterCollection(Collection<Character> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	Collection<Boolean> readBooleanCollection(Collection<Boolean> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	Collection<String> readStringCollection(Collection<String> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	<E extends Enum<E>> Collection<E> readEnumCollection(Class<E> enumType, Collection<E> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	Collection<Date> readDateCollection(Collection<Date> collection, boolean nullableCollection, boolean nullableElements) throws IOException;

	<T, C extends Collection<T>> C readObjectCollection(C collection, boolean nullableCollection, boolean nullableElements, IDataSerializer<T> serializer) throws IOException;

	<T> T readObject(boolean nullable, IDataSerializer<T> serializer) throws IOException;

}
