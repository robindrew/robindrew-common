package com.robindrew.sizeof.definition.type;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.robindrew.sizeof.IMemoryCalculator;
import com.robindrew.sizeof.architecture.IArchitecture;
import com.robindrew.sizeof.definition.ClassDefinition;
import com.robindrew.sizeof.definition.IClassDefinition;
import com.robindrew.sizeof.definition.IClassDefinitionMap;

/**
 * An Object Definition.
 */
@SuppressWarnings("unchecked")
public final class ObjectDefinition<I> extends ClassDefinition<I> {

	/** The class. */
	private final Class<I> clazz;
	/** The size. */
	private final long size;
	/** The object fields. */
	private final Field[] objectFields;
	/** The primitive fields. */
	private final Field[] primitiveFields;
	/** The operating system. */
	private final IArchitecture architecture;
	/** Linked definition. */
	private IClassDefinition<I> link;

	/**
	 * Creates a new definition.
	 * @param clazz the class.
	 * @param definitionMap the definition map.
	 */
	public ObjectDefinition(Class<I> clazz, IClassDefinitionMap definitionMap, IArchitecture architecture) {
		this.clazz = clazz;
		this.architecture = architecture;

		// Calculate size & find fields
		long size = architecture.sizeOfObjectInstance();
		List<Field> objectFieldList = new ArrayList<Field>();
		List<Field> primitiveFieldList = new ArrayList<Field>();
		Class<?> type = clazz;
		while (!type.equals(Object.class)) {

			// Link existing definition
			if (definitionMap.contains(type)) {
				IClassDefinition<?> definition = definitionMap.get(type);
				if (!(definition instanceof IgnoreDefinition)) {
					link = (IClassDefinition<I>) definition;
				}
				break;
			}

			// Iterate fields
			Field[] fields = type.getDeclaredFields();
			for (Field field : fields) {
				int modifiers = field.getModifiers();
				if (!Modifier.isStatic(modifiers)) {
					if (field.getType().isPrimitive()) {
						size += architecture.sizeOfPrimitive(field.getType());
						primitiveFieldList.add(field);
					} else {
						size += architecture.sizeOfNullPointer();
						if (!definitionMap.shouldIgnoreField(field)) {
							field.setAccessible(true);
							objectFieldList.add(field);
						}
					}
				}
			}
			type = type.getSuperclass();
		}

		// Done
		this.objectFields = objectFieldList.toArray(new Field[objectFieldList.size()]);
		this.primitiveFields = primitiveFieldList.toArray(new Field[primitiveFieldList.size()]);
		this.size = size;
	}

	public Field[] getObjectFields() {
		return objectFields;
	}

	public Field[] getPrimitiveFields() {
		return primitiveFields;
	}

	/**
	 * Returns the class.
	 * @return the class.
	 */
	public final Class<I> getClazz() {
		return clazz;
	}

	@Override
	public long sizeOf(I instance, IMemoryCalculator calculator) {
		if (link != null) {
			return link.sizeOf(instance, calculator) + size;
		}
		return size;
	}

	@Override
	public final boolean hasElements(I instance) {
		if (link != null && link.hasElements(instance)) {
			return true;
		}
		return objectFields.length > 0;
	}

	@Override
	public long sizeOfElements(I instance, IMemoryCalculator calculator, long size) {
		if (link != null) {
			size = link.sizeOfElements(instance, calculator, size);
			// extends class, so only one instance
			size -= architecture.sizeOfObjectInstance();
		}
		for (Field field : objectFields) {
			Object value = get(field, instance);
			if (value != null) {
				size += calculator.sizeOf(value);
			}
		}
		return size;
	}

}
