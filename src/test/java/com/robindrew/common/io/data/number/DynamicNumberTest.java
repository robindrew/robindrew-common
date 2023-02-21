package com.robindrew.common.io.data.number;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class DynamicNumberTest {

	@Test
	public void testReadWriteLong() throws Exception {

		List<Long> expectedList = new ArrayList<>();
		expectedList.add(0l);
		for (long value = -1; value > Long.MIN_VALUE && value < 0; value *= 2) {
			expectedList.add(value);
		}
		expectedList.add(Long.MIN_VALUE + 1);
		for (long value = 1; value < Long.MAX_VALUE && value > 0; value *= 2) {
			expectedList.add(value);
		}
		expectedList.add(Long.MAX_VALUE);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		for (long expected : expectedList) {
			DynamicNumber.writeLong(output, expected);
		}

		byte[] bytes = output.toByteArray();

		ByteArrayInputStream input = new ByteArrayInputStream(bytes);
		for (long expected : expectedList) {
			long actual = DynamicNumber.readLong(input);
			Assert.assertEquals(expected, actual);
		}
	}

	@Test
	public void testReadWriteInt() throws Exception {

		List<Integer> expectedList = new ArrayList<>();
		expectedList.add(0);
		for (int value = -1; value > Integer.MIN_VALUE && value < 0; value *= 2) {
			expectedList.add(value);
		}
		expectedList.add(Integer.MIN_VALUE);
		for (int value = 1; value < Integer.MAX_VALUE && value > 0; value *= 2) {
			expectedList.add(value);
		}
		expectedList.add(Integer.MAX_VALUE);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		for (int expected : expectedList) {
			DynamicNumber.writeInt(output, expected);
		}

		byte[] bytes = output.toByteArray();

		ByteArrayInputStream input = new ByteArrayInputStream(bytes);
		for (int expected : expectedList) {
			int actual = DynamicNumber.readInt(input);
			Assert.assertEquals(expected, actual);
		}
	}

	@Test
	public void testReadWriteShort() throws Exception {

		List<Short> expectedList = new ArrayList<>();
		expectedList.add((short) 0);
		for (short value = -1; value > Short.MIN_VALUE && value < 0; value *= 2) {
			expectedList.add(value);
		}
		expectedList.add(Short.MIN_VALUE);
		for (short value = 1; value < Short.MAX_VALUE && value > 0; value *= 2) {
			expectedList.add(value);
		}
		expectedList.add(Short.MAX_VALUE);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		for (short expected : expectedList) {
			DynamicNumber.writeShort(output, expected);
		}

		byte[] bytes = output.toByteArray();

		ByteArrayInputStream input = new ByteArrayInputStream(bytes);
		for (short expected : expectedList) {
			short actual = DynamicNumber.readShort(input);
			Assert.assertEquals(expected, actual);
		}
	}

	@Test
	public void testReadWriteByte() throws Exception {

		List<Byte> expectedList = new ArrayList<>();
		expectedList.add((byte) 0);
		for (byte value = -1; value > Byte.MIN_VALUE && value < 0; value *= 2) {
			expectedList.add(value);
		}
		expectedList.add(Byte.MIN_VALUE);
		for (byte value = 1; value < Byte.MAX_VALUE && value > 0; value *= 2) {
			expectedList.add(value);
		}
		expectedList.add(Byte.MAX_VALUE);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		for (byte expected : expectedList) {
			DynamicNumber.writeByte(output, expected);
		}

		byte[] bytes = output.toByteArray();

		ByteArrayInputStream input = new ByteArrayInputStream(bytes);
		for (byte expected : expectedList) {
			byte actual = DynamicNumber.readByte(input);
			Assert.assertEquals(expected, actual);
		}
	}

}
