package com.robindrew.common.codec;

import java.util.Random;

public class RandomBinaryDecoder implements IDecoder {

	private final Random random;

	public RandomBinaryDecoder(Random random) {
		if (random == null) {
			throw new NullPointerException("random");
		}
		this.random = random;
	}

	public RandomBinaryDecoder(long seed) {
		this(new Random(seed));
	}

	@Override
	public String decodeToString(byte[] data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] decodeToBytes(byte[] data) {
		byte[] decoded = new byte[data.length];
		random.nextBytes(decoded);
		for (int i = 0; i < data.length; i++) {
			decoded[i] = (byte) (data[i] - decoded[i] - i);
		}
		return decoded;
	}
}
