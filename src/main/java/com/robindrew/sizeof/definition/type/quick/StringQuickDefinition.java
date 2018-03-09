package com.robindrew.sizeof.definition.type.quick;

import com.robindrew.sizeof.IMemoryCalculator;
import com.robindrew.sizeof.definition.ClassDefinition;

/**
 * A String Definition.
 * <p>
 * WARNING: This definition completely ignores the substring/split issue. It assumes all {@link String}s are backed by a
 * char[] of the same length as the String itself. Strings created from the {@link String#substring()} methods or
 * {@link String#split()} methods will NOT have their memory accurately calculated.
 * </p>
 */
public final class StringQuickDefinition extends ClassDefinition<String> {

	@Override
	public long sizeOf(String text, IMemoryCalculator calculator) {
		return 24 + calculator.getArchitecture().sizeOfShortArray(text.length());
	}

}
