package com.robindrew.common.io.data.number;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PositiveNumber {

	public static void writeByte(OutputStream output, byte value) throws IOException {
		writeLong(output, value);
	}

	public static void writeShort(OutputStream output, short value) throws IOException {
		writeLong(output, value);
	}

	public static void writeInt(OutputStream output, int value) throws IOException {
		writeLong(output, value);
	}

	public static void writeLong(OutputStream output, long value) throws IOException {
		if (value < 0) {
			throw new IllegalArgumentException("value=" + value);
		}
		if (value == 0) {
			output.write((byte) 0);
			return;
		}
		do {
			byte write = (byte) (value & 127l);
			value >>= 7;
			if (value > 0) {
				write |= -128;
			}
			output.write(write);
		} while (value > 0);
	}

	public static long readLong(InputStream input) throws IOException {
		int count = 0;
		long number = 0;
		long read = 0;
		do {
			read = input.read();
			if (read == -1) {
				throw new IOException("End of stream");
			}
			number |= (read & 127l) << (count * 7);
			count++;
		} while ((read & -128l) != 0);
		if (number < 0) {
			throw new IOException("read=" + number);
		}
		return number;
	}

	public static int readInt(InputStream input) throws IOException {
		long read = readLong(input);
		if (read > Integer.MAX_VALUE) {
			throw new IOException("read=" + read);
		}
		return (int) read;
	}

	public static short readShort(InputStream input) throws IOException {
		long read = readLong(input);
		if (read > Short.MAX_VALUE) {
			throw new IOException("read=" + read);
		}
		return (short) read;
	}

	public static byte readByte(InputStream input) throws IOException {
		long read = readLong(input);
		if (read > Byte.MAX_VALUE) {
			throw new IOException("read=" + read);
		}
		return (byte) read;
	}

}
