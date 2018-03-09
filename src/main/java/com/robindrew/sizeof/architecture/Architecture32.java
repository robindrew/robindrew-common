package com.robindrew.sizeof.architecture;

/**
 * The 32-bit JVM architecture.
 */
public class Architecture32 implements IArchitecture {

	/** The number of bytes used to represent a byte. */
	public static final int SIZE_OF_BYTE = 1;
	/** The number of bytes used to represent a byte. */
	public static final int SIZE_OF_SHORT = 2;
	/** The number of bytes used to represent a byte. */
	public static final int SIZE_OF_INT = 4;
	/** The number of bytes used to represent a byte. */
	public static final int SIZE_OF_LONG = 8;

	/** The number of bytes used to represent a boolean. */
	public static final int SIZE_OF_BOOLEAN = 1;
	/** The number of bytes used to represent a char. */
	public static final int SIZE_OF_CHAR = 2;
	/** The number of bytes used to represent a byte. */
	public static final int SIZE_OF_FLOAT = 4;
	/** The number of bytes used to represent a byte. */
	public static final int SIZE_OF_DOUBLE = 8;

	/** The number of bytes used to represent a pointer field (unassigned/null). */
	public static final int SIZE_OF_NULL_POINTER = 4;
	/** The number of bytes used to represent an object (with no fields). */
	public static final int SIZE_OF_OBJECT_INSTANCE = 8;
	/** The number of bytes any object must be a multiple of. */
	public static final int SIZE_OF_OBJECT_MULTIPLE = 8;
	/** The number of bytes used to represent an array (before calulating contents). */
	public static final int SIZE_OF_ARRAY = 16;

	@Override
	public final long sizeOfPrimitive(Class<?> type) {
		// Using == is a huge speed improvement over equals(), or using a map
		// Does this work ok with ClassLoaders ...?
		if (type == boolean.class) {
			return SIZE_OF_BOOLEAN;
		}
		if (type == char.class) {
			return SIZE_OF_CHAR;
		}
		if (type == byte.class) {
			return SIZE_OF_BYTE;
		}
		if (type == short.class) {
			return SIZE_OF_SHORT;
		}
		if (type == int.class) {
			return SIZE_OF_INT;
		}
		if (type == long.class) {
			return SIZE_OF_LONG;
		}
		if (type == float.class) {
			return SIZE_OF_FLOAT;
		}
		if (type == double.class) {
			return SIZE_OF_DOUBLE;
		}
		throw new IllegalArgumentException("type is not primitive: " + type);
	}

	@Override
	public final long sizeOfLongArray(long length) {
		return SIZE_OF_ARRAY + (length * 8);
	}

	@Override
	public final long sizeOfIntArray(long length) {
		if (length <= 1) {
			return SIZE_OF_ARRAY;
		}
		length -= 1;
		return SIZE_OF_ARRAY + ((length / 2) * 8) + (length % 2 == 0 ? 0 : 8);
	}

	@Override
	public final long sizeOfShortArray(long length) {
		if (length <= 2) {
			return SIZE_OF_ARRAY;
		}
		length -= 2;
		return SIZE_OF_ARRAY + ((length / 4) * 8) + (length % 4 == 0 ? 0 : 8);
	}

	@Override
	public final long sizeOfByteArray(long length) {
		if (length <= 4) {
			return SIZE_OF_ARRAY;
		}
		length -= 4;
		return SIZE_OF_ARRAY + ((length / 8) * 8) + (length % 8 == 0 ? 0 : 8);
	}

	@Override
	public final long checkSize(long size) {
		// All objects occupy a multiple of 8 (SIZE_OF_OBJECT_INSTANCE) bytes
		if (size % SIZE_OF_OBJECT_MULTIPLE == 0) {
			return size;
		}
		return ((size / SIZE_OF_OBJECT_MULTIPLE) + 1) * SIZE_OF_OBJECT_MULTIPLE;
	}

	@Override
	public final long sizeOfObjectInstance() {
		return SIZE_OF_OBJECT_INSTANCE;
	}

	@Override
	public final long sizeOfNullPointer() {
		return SIZE_OF_NULL_POINTER;
	}

	@Override
	public final long sizeOfBooleanArray(long length) {
		return sizeOfByteArray(length);
	}

	@Override
	public final long sizeOfCharArray(long length) {
		return sizeOfShortArray(length);
	}

	@Override
	public final long sizeOfFloatArray(long length) {
		return sizeOfIntArray(length);
	}

	@Override
	public final long sizeOfDoubleArray(long length) {
		return sizeOfLongArray(length);
	}

	@Override
	public final long sizeOfObjectArray(int length) {
		return sizeOfIntArray(length);
	}

}
