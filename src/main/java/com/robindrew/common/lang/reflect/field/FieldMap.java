package com.robindrew.common.lang.reflect.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A Map of non-static fields (which are all accessible.
 */
public final class FieldMap implements Iterable<IField> {

	/** The class to field cache map. */
	private static final Map<Class<?>, FieldMap> classToFieldMap = new HashMap<Class<?>, FieldMap>();

	/**
	 * Returns a field map for the given class.
	 * @param clazz the class.
	 * @return a field map for the given class.
	 */
	public static final FieldMap getInstance(Class<?> clazz) {
		synchronized (classToFieldMap) {
			FieldMap fieldMap = classToFieldMap.get(clazz);
			if (fieldMap == null) {
				fieldMap = new FieldMap(clazz);
				classToFieldMap.put(clazz, fieldMap);
			}
			return fieldMap;
		}
	}

	/**
	 * Returns a field map for the class of the given object.
	 * @param object the object.
	 * @return a field map for the class of the given object.
	 */
	public static final FieldMap getInstance(Object object) {
		return getInstance(object.getClass());
	}

	/** The underlying class. */
	private final Class<?> clazz;
	/** The field list. */
	private final List<IField> fieldList;
	/** The field list. */
	private final Set<String> fieldNameSet = new LinkedHashSet<String>();
	/** The field map. */
	private final Map<String, IField> nameToFieldMap = new LinkedHashMap<String, IField>();

	/**
	 * Creates a new field map.
	 * @param clazz the class.
	 */
	public FieldMap(Class<?> clazz) {
		if (clazz == null) {
			throw new NullPointerException();
		}
		this.clazz = clazz;

		// Populate fields
		this.fieldList = new FieldLister().getFieldList(clazz);
		for (IField field : fieldList) {
			fieldNameSet.add(field.getName());
			nameToFieldMap.put(field.getName(), field);
		}
	}

	/**
	 * Returns the field name set.
	 * @return the field name set.
	 */
	public Set<String> getFieldNameSet() {
		return Collections.unmodifiableSet(fieldNameSet);
	}

	/**
	 * Returns the field list.
	 * @return the field list.
	 */
	public List<IField> getFieldList() {
		return Collections.unmodifiableList(fieldList);
	}

	/**
	 * Returns the field list.
	 * @param assignableFrom the class the fields are assignable from.
	 * @return the field list.
	 */
	public List<IField> getFieldList(Class<?> assignableFrom) {
		List<IField> fieldList = new ArrayList<IField>();
		for (IField field : getFieldList()) {
			if (assignableFrom.isAssignableFrom(field.getType())) {
				fieldList.add(field);
			}
		}
		return fieldList;
	}

	/**
	 * Returns true if this map contains the named field.
	 * @param name the name.
	 * @return true if this map contains the named field.
	 */
	public boolean containsField(String name) {
		return nameToFieldMap.containsKey(name);
	}

	/**
	 * Returns the named field.
	 * @param name the name.
	 * @return the named field.
	 */
	public IField getField(String name) {
		IField field = nameToFieldMap.get(name);
		if (field == null) {
			throw new IllegalArgumentException("Unknown field: " + name + " in class: " + clazz);
		}
		return field;
	}

	@Override
	public Iterator<IField> iterator() {
		return getFieldList().iterator();
	}
}
