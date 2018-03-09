package com.robindrew.sizeof.definition.type;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Set;

import com.robindrew.sizeof.IMemoryCalculator;
import com.robindrew.sizeof.definition.ClassDefinition;

/**
 * A Linked List Definition (Safe).
 * <p/>
 * This definition is 'safe' because it adds the Entry objects to the instance list, unlike the {@link LinkedList
 * QuickDefinition} version which takes approximately half as long to calculate sizes. It also provides a minor
 * performance enhancement on the default calculation strategy.
 */
@SuppressWarnings("rawtypes")
public class LinkedListDefinition extends ClassDefinition<LinkedList> {

	/**
	 * The LinkedList$header field.
	 */
	private final Field headerField;
	/**
	 * The LinkedList$Entry class.
	 */
	private final Class entryClass;
	/**
	 * The Entry.element field.
	 */
	private final Field elementField;
	/**
	 * The Entry.next field.
	 */
	private final Field nextField;

	/**
	 * Creates a linked list definition.
	 */
	public LinkedListDefinition() throws ClassNotFoundException, NoSuchFieldException {
		headerField = LinkedList.class.getDeclaredField("header");
		headerField.setAccessible(true);
		entryClass = Class.forName("java.util.LinkedList$Entry");
		elementField = entryClass.getDeclaredField("element");
		elementField.setAccessible(true);
		nextField = entryClass.getDeclaredField("next");
		nextField.setAccessible(true);
	}

	@Override
	public long sizeOf(LinkedList list, IMemoryCalculator calculator) {
		if (list == null)
			return 0;
		return 44 + ((long) list.size() * 24);
	}

	@Override
	public final boolean hasElements(LinkedList list) {
		return true;
	}

	@Override
	public long sizeOfElements(LinkedList list, IMemoryCalculator calculator, long size) {
		if (list == null) {
			return 0;
		}

		int listSize = list.size();

		// Iterate over entries
		Object link = get(headerField, list);
		if (link == null) {
			return 0;
		}

		Set<Object> set = calculator.getInstanceSet();
		set.add(link);
		for (int i = 0; i < listSize; i++) {
			link = get(nextField, link);
			if (link != null) {
				set.add(link);
				Object element = get(elementField, link);
				if (element != null) {
					size += calculator.sizeOf(element);
				}
			} else {
				// hit the end of the list
				break;
			}

		}
		return size;
	}

}
