package com.robindrew.common.codec;

import java.io.File;
import java.io.InputStream;
import java.util.Random;

public class RandomBinaryEncoder implements IEncoder {

	private final Random random;

	public RandomBinaryEncoder(Random random) {
		if (random == null) {
			throw new NullPointerException("random");
		}
		this.random = random;
	}

	public RandomBinaryEncoder(long seed) {
		this(new Random(seed));
	}

	@Override
	public byte[] encodeToBytes(byte[] data) {
		byte[] encoded = new byte[data.length];
		random.nextBytes(encoded);
		for (int i = 0; i < data.length; i++) {
			encoded[i] = (byte) (data[i] + encoded[i] + i);
		}
		return encoded;
	}

	@Override
	public byte[] encodeToBytes(String text) {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] encodeToBytes(InputStream input) {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] encodeToBytes(File file) {
		throw new UnsupportedOperationException();
	}
}
