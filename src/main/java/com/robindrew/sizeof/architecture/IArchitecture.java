package com.robindrew.sizeof.architecture;

/**
 * The Architecture Model.
 * <p>
 * Provides architecture-specific calculations for basic types.
 * </p>
 */
public interface IArchitecture {

	/**
	 * Returns the size in bytes of an object instance.
	 * @return the size in bytes.
	 */
	long sizeOfObjectInstance();

	/**
	 * Returns the size in bytes of an null pointer reference.
	 * @return the size in bytes.
	 */
	long sizeOfNullPointer();

	/**
	 * Returns the size in bytes of the given primitive type.
	 * @param type the type.
	 * @return the size in bytes.
	 */
	long sizeOfPrimitive(Class<?> type);

	/**
	 * Returns the size of a long array with the given length.
	 * @param length the array length
	 * @return the size of the array.
	 */
	long sizeOfLongArray(long length);

	/**
	 * Returns the size of an int array with the given length.
	 * @param length the array length
	 * @return the size of the array.
	 */
	long sizeOfIntArray(long length);

	/**
	 * Returns the size of a short array with the given length.
	 * @param length the array length
	 * @return the size of the array.
	 */
	long sizeOfShortArray(long length);

	/**
	 * Returns the size of a byte array with the given length.
	 * @param length the array length
	 * @return the size of the array.
	 */
	long sizeOfByteArray(long length);

	/**
	 * Returns the size of a boolean array with the given length.
	 * @param length the array length
	 * @return the size of the array.
	 */
	long sizeOfBooleanArray(long length);

	/**
	 * Returns the size of a char array with the given length.
	 * @param length the array length
	 * @return the size of the array.
	 */
	long sizeOfCharArray(long length);

	/**
	 * Returns the size of a float array with the given length.
	 * @param length the array length
	 * @return the size of the array.
	 */
	long sizeOfFloatArray(long length);

	/**
	 * Returns the size of a double array with the given length.
	 * @param length the array length
	 * @return the size of the array.
	 */
	long sizeOfDoubleArray(long length);

	/**
	 * Returns the size of an object array with the given length.
	 * @param length the array length
	 * @return the size of the array.
	 */
	long sizeOfObjectArray(int length);

	/**
	 * Returns correct size for an object (for example rounding up to the nearest multiple of 8).
	 * @param size the size to correct.
	 * @return the corrected size.
	 */
	long checkSize(long size);

}
