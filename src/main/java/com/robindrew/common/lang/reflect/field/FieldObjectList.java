package com.robindrew.common.lang.reflect.field;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A Field Object List.
 */
public class FieldObjectList<F> implements Iterable<F> {

	/** The serialVersionUID. */
	public static final long serialVersionUID = -3960492791575042260L;

	/** The object instance. */
	private final Object instance;
	/** The field list. */
	private final List<IField> fieldList;

	/**
	 * Creates a new field list.
	 * @param instance the instance.
	 * @param assignableClass the assignable class.
	 */
	public FieldObjectList(Object instance, Class<F> assignableClass) {
		FieldMap map = FieldMap.getInstance(instance);
		this.fieldList = map.getFieldList(assignableClass);
		this.instance = instance;
	}

	/**
	 * Returns the instance at the given index.
	 * @param index the index.
	 * @return the instance.
	 */
	public F getInstance(int index) {
		IField field = fieldList.get(index);
		return getInstance(field);
	}

	/**
	 * Returns the instance for the given field.
	 * @param field the field.
	 * @return the instance.
	 */
	@SuppressWarnings("unchecked")
	private F getInstance(IField field) {
		return (F) field.get(instance);
	}

	/**
	 * Returns the elements as a list.
	 * @return the elements as a list.
	 */
	public List<F> toList() {
		List<F> list = new ArrayList<F>();
		for (IField field : fieldList) {
			list.add(getInstance(field));
		}
		return list;
	}

	@Override
	public Iterator<F> iterator() {
		return toList().iterator();
	}
}
