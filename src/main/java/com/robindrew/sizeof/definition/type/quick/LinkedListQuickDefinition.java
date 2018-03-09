package com.robindrew.sizeof.definition.type.quick;

import java.util.LinkedList;

import com.robindrew.sizeof.IMemoryCalculator;
import com.robindrew.sizeof.definition.ClassDefinition;

/**
 * A LinkedList Quick Definition.
 * <p/>
 * This definition for {@link LinkedList}s is over twice as fast as the default definition, however it does NOT record
 * the Entry instances backing its elements. Although this is an issue, it is not particularly unsafe, as very rarely
 * will anyone reference the Entry instances externally.
 */
public class LinkedListQuickDefinition extends ClassDefinition<LinkedList<?>> {

	@Override
	public long sizeOf(LinkedList<?> list, IMemoryCalculator calculator) {
		return 44 + ((long) list.size() * 24);
	}

	@Override
	public final boolean hasElements(LinkedList<?> list) {
		return !list.isEmpty();
	}

	@Override
	public long sizeOfElements(LinkedList<?> list, IMemoryCalculator calculator, long size) {
		for (Object element : list) {
			if (element != null) {
				size += calculator.sizeOf(element);
			}
		}
		return size;
	}

}
