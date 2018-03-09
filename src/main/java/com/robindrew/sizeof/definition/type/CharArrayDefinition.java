package com.robindrew.sizeof.definition.type;

import com.robindrew.sizeof.IMemoryCalculator;
import com.robindrew.sizeof.definition.ClassDefinition;

/**
 * A Char Array Definition.
 */
public final class CharArrayDefinition extends ClassDefinition<char[]> {

    @Override
	public long sizeOf(char[] array, IMemoryCalculator calculator) {
        return calculator.getArchitecture().sizeOfCharArray(array.length);
    }

}
