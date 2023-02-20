package com.robindrew.common.io.data.number;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DynamicNumber {

	public static final long BYTE_1 = 255l;
	public static final long BYTE_2 = 65535l;
	public static final long BYTE_3 = 16777215l;
	public static final long BYTE_4 = 4294967295l;
	public static final long BYTE_5 = 1099511627775l;
	public static final long BYTE_6 = 281474976710655l;
	public static final long BYTE_7 = 72057594037927935l;

	public static final byte POSITIVE_1 = 120;
	public static final byte POSITIVE_2 = 121;
	public static final byte POSITIVE_3 = 122;
	public static final byte POSITIVE_4 = 123;
	public static final byte POSITIVE_5 = 124;
	public static final byte POSITIVE_6 = 125;
	public static final byte POSITIVE_7 = 126;
	public static final byte POSITIVE_8 = 127;

	public static final byte NEGATIVE_1 = -121;
	public static final byte NEGATIVE_2 = -122;
	public static final byte NEGATIVE_3 = -123;
	public static final byte NEGATIVE_4 = -124;
	public static final byte NEGATIVE_5 = -125;
	public static final byte NEGATIVE_6 = -126;
	public static final byte NEGATIVE_7 = -127;
	public static final byte NEGATIVE_8 = -128;

	private static int read(InputStream input) throws IOException {
		int read = input.read();
		if (read == -1) {
			throw new IOException("End of stream");
		}
		return read;
	}

	public static long readLong(byte[] input, int offset) throws IOException {

		// Simple byte read
		byte byte1 = input[offset++];
		if (NEGATIVE_1 < byte1 && byte1 < POSITIVE_1) {
			return byte1;
		}

		boolean negative = byte1 < 0;
		int bytes = countReadBytes(byte1);

		long value = 0;
		for (int i = 0; i < bytes; i++) {
			int read = input[offset++];
			value |= ((long) (read)) << (i * 8);
		}
		return negative ? -value : value;
	}

	public static long readLong(InputStream input) throws IOException {

		// Simple byte read
		byte byte1 = (byte) read(input);
		if (NEGATIVE_1 < byte1 && byte1 < POSITIVE_1) {
			return byte1;
		}

		boolean negative = byte1 < 0;
		int bytes = countReadBytes(byte1);

		long value = 0;
		for (int i = 0; i < bytes; i++) {
			int read = read(input);
			value |= ((long) (read)) << (i * 8);
		}
		return negative ? -value : value;
	}

	private static int countReadBytes(byte value) {
		if (value >= 0) {
			return value - (POSITIVE_1 - 1);
		} else {
			return (-value) + (NEGATIVE_1 + 1);
		}
	}

	public static int readInt(InputStream input) throws IOException {
		long read = readLong(input);
		if (read < Integer.MIN_VALUE || read > Integer.MAX_VALUE) {
			throw new IOException("read=" + read);
		}
		return (int) read;
	}

	public static short readShort(InputStream input) throws IOException {
		long read = readLong(input);
		if (read < Short.MIN_VALUE || read > Short.MAX_VALUE) {
			throw new IOException("read=" + read);
		}
		return (short) read;
	}

	public static byte readByte(InputStream input) throws IOException {
		long read = readLong(input);
		if (read < Byte.MIN_VALUE || read > Byte.MAX_VALUE) {
			throw new IOException("read=" + read);
		}
		return (byte) read;
	}

	public static void writeByte(OutputStream output, byte value) throws IOException {
		writeLong(output, value);
	}

	public static void writeShort(OutputStream output, short value) throws IOException {
		writeLong(output, value);
	}

	public static void writeInt(OutputStream output, int value) throws IOException {
		writeLong(output, value);
	}

	public static void writeLong(byte[] output, int offset, long value) throws IOException {
		if (value == Long.MIN_VALUE) {
			throw new IllegalArgumentException("Long.MIN_VALUE not supported");
		}

		// Simple byte write
		if (NEGATIVE_1 < value && value < POSITIVE_1) {
			output[offset++] = ((byte) value);
			return;
		}

		// Set negative flag
		boolean negative = value < 0;
		if (negative) {
			value = -value;
		}
		int bytes = countWriteBytes(output, offset++, value, negative);

		// Write bytes
		while (bytes > 0) {
			output[offset++] = (byte) (value & 255l);
			value >>= 8;
			bytes--;
		}
	}

	public static void writeLong(OutputStream output, long value) throws IOException {
		if (value == Long.MIN_VALUE) {
			throw new IllegalArgumentException("Long.MIN_VALUE not supported");
		}

		// Simple byte write
		if (NEGATIVE_1 < value && value < POSITIVE_1) {
			output.write((byte) value);
			return;
		}

		// Set negative flag
		boolean negative = value < 0;
		if (negative) {
			value = -value;
		}
		int bytes = countWriteBytes(output, value, negative);

		// Write bytes
		while (bytes > 0) {
			output.write((byte) (value & 255l));
			value >>= 8;
			bytes--;
		}
	}

	private static int countWriteBytes(OutputStream output, long value, boolean negative) throws IOException {
		if (value <= BYTE_1) {
			output.write(negative ? NEGATIVE_1 : POSITIVE_1);
			return 1;
		}
		if (value <= BYTE_2) {
			output.write(negative ? NEGATIVE_2 : POSITIVE_2);
			return 2;
		}
		if (value <= BYTE_3) {
			output.write(negative ? NEGATIVE_3 : POSITIVE_3);
			return 3;
		}
		if (value <= BYTE_4) {
			output.write(negative ? NEGATIVE_4 : POSITIVE_4);
			return 4;
		}
		if (value <= BYTE_5) {
			output.write(negative ? NEGATIVE_5 : POSITIVE_5);
			return 5;
		}
		if (value <= BYTE_6) {
			output.write(negative ? NEGATIVE_6 : POSITIVE_6);
			return 6;
		}
		if (value <= BYTE_7) {
			output.write(negative ? NEGATIVE_7 : POSITIVE_7);
			return 7;
		}
		output.write(negative ? NEGATIVE_8 : POSITIVE_8);
		return 8;
	}

	private static int countWriteBytes(byte[] output, int offset, long value, boolean negative) throws IOException {
		if (value <= BYTE_1) {
			output[offset] = (negative ? NEGATIVE_1 : POSITIVE_1);
			return 1;
		}
		if (value <= BYTE_2) {
			output[offset] = (negative ? NEGATIVE_2 : POSITIVE_2);
			return 2;
		}
		if (value <= BYTE_3) {
			output[offset] = (negative ? NEGATIVE_3 : POSITIVE_3);
			return 3;
		}
		if (value <= BYTE_4) {
			output[offset] = (negative ? NEGATIVE_4 : POSITIVE_4);
			return 4;
		}
		if (value <= BYTE_5) {
			output[offset] = (negative ? NEGATIVE_5 : POSITIVE_5);
			return 5;
		}
		if (value <= BYTE_6) {
			output[offset] = (negative ? NEGATIVE_6 : POSITIVE_6);
			return 6;
		}
		if (value <= BYTE_7) {
			output[offset] = (negative ? NEGATIVE_7 : POSITIVE_7);
			return 7;
		}
		output[offset] = (negative ? NEGATIVE_8 : POSITIVE_8);
		return 8;
	}

}
