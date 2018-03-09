package com.robindrew.sizeof.definition.type;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.robindrew.sizeof.IMemoryCalculator;
import com.robindrew.sizeof.definition.ClassDefinition;

/**
 * A Hash Map Definition (Safe).
 * <p>
 * This definition is 'safe' because it adds the Entry objects to the instance list. It also provides a minor
 * performance enhancement on the default calculation strategy.
 * </p>
 * @see HashMap
 * @see HashSet
 */
@SuppressWarnings("rawtypes")
public class HashMapDefinition extends ClassDefinition<HashMap> {

	/**
	 * The HashMap.table field.
	 */
	private final Field tableField;
	/**
	 * The HashMap$Entry class.
	 */
	private final Class<?> entryClass;
	/**
	 * The Entry.key field.
	 */
	private final Field keyField;
	/**
	 * The Entry.value field.
	 */
	private final Field valueField;
	/**
	 * The Entry.next field.
	 */
	private final Field nextField;

	/**
	 * Creates a linked list definition.
	 */
	public HashMapDefinition() throws ClassNotFoundException, NoSuchFieldException {
		tableField = HashMap.class.getDeclaredField("table");
		tableField.setAccessible(true);
		entryClass = Class.forName("java.util.HashMap$Entry");
		keyField = entryClass.getDeclaredField("key");
		keyField.setAccessible(true);
		valueField = entryClass.getDeclaredField("value");
		valueField.setAccessible(true);
		nextField = entryClass.getDeclaredField("next");
		nextField.setAccessible(true);
	}

	@Override
	public long sizeOf(HashMap map, IMemoryCalculator calculator) {
		return 36; // HashMap instance base size
	}

	@Override
	public final boolean hasElements(HashMap map) {
		return true;
	}

	@Override
	public long sizeOfElements(HashMap map, IMemoryCalculator calculator, long size) {
		int mapSize = map.size();
		Object[] table = get(tableField, map);
		Set<Object> set = calculator.getInstanceSet();
		set.add(table);
		size += calculator.getArchitecture().sizeOfIntArray(table.length);

		// Empty
		if (mapSize == 0) {
			return size;
		}

		// Iterate over entries
		for (Object entry : table) {
			while (entry != null) {
				set.add(entry);
				size += 24; // Entry instance base size

				Object key = get(keyField, entry);
				Object value = get(valueField, entry);
				if (key != null) {
					size += calculator.sizeOf(key);
				}
				if (key != value && value != null) {
					size += calculator.sizeOf(value);
				}

				// Short circuit - helps when small or poorly distributed
				mapSize--;
				if (mapSize == 0) {
					break;
				}

				// Next?
				entry = get(nextField, entry);
			}
		}
		return size;
	}

}
