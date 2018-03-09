package com.robindrew.sizeof.definition.type;

import com.robindrew.sizeof.IMemoryCalculator;
import com.robindrew.sizeof.definition.ClassDefinition;

/**
 * A Byte Array Definition.
 */
public final class ByteArrayDefinition extends ClassDefinition<byte[]> {

	@Override
	public long sizeOf(byte[] array, IMemoryCalculator calculator) {
		return calculator.getArchitecture().sizeOfByteArray(array.length);
	}

}
