package com.robindrew.common.lang;

public class Bits {

	public static String toString(long value) {
		char[] chars = new char[64];
		byteToChars((byte) (value >>> 56), chars, 0);
		byteToChars((byte) (value >>> 48), chars, 8);
		byteToChars((byte) (value >>> 40), chars, 16);
		byteToChars((byte) (value >>> 32), chars, 24);
		byteToChars((byte) (value >>> 24), chars, 32);
		byteToChars((byte) (value >>> 16), chars, 40);
		byteToChars((byte) (value >>> 8), chars, 48);
		byteToChars((byte) (value >>> 0), chars, 56);
		return new String(chars);
	}

	public static String toString(int value) {
		char[] chars = new char[32];
		byteToChars((byte) (value >>> 24), chars, 0);
		byteToChars((byte) (value >>> 16), chars, 8);
		byteToChars((byte) (value >>> 8), chars, 16);
		byteToChars((byte) (value >>> 0), chars, 24);
		return new String(chars);
	}

	public static String toString(short value) {
		char[] chars = new char[16];
		byteToChars((byte) (value >>> 8), chars, 0);
		byteToChars((byte) (value >>> 0), chars, 8);
		return new String(chars);
	}

	public static String toString(byte value) {
		char[] chars = new char[8];
		byteToChars(value, chars, 0);
		return new String(chars);
	}

	public static String toString(byte[] values) {
		char[] chars = new char[values.length * 8];
		int offset = 0;
		for (byte value : values) {
			byteToChars(value, chars, offset);
			offset += 8;
		}
		return new String(chars);
	}

	public static void byteToChars(byte value, char[] chars, int offset) {
		chars[offset + 0] = (value & 128) > 0 ? '1' : '0';
		chars[offset + 1] = (value & 64) > 0 ? '1' : '0';
		chars[offset + 2] = (value & 32) > 0 ? '1' : '0';
		chars[offset + 3] = (value & 16) > 0 ? '1' : '0';
		chars[offset + 4] = (value & 8) > 0 ? '1' : '0';
		chars[offset + 5] = (value & 4) > 0 ? '1' : '0';
		chars[offset + 6] = (value & 2) > 0 ? '1' : '0';
		chars[offset + 7] = (value & 1) > 0 ? '1' : '0';
	}
}
