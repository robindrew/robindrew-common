package com.robindrew.common.lang.reflect.field;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Fields {

	public static final IField get(Class<?> type, String name, boolean recursive) {
		IField field = findField(type, name, recursive);
		if (field == null) {
			throw new IllegalArgumentException("Field " + name + " not found in " + type);
		}
		return field;
	}

	public static final IField get(Class<?> type, String name) {
		return get(type, name, true);
	}

	private static final IField findField(Class<?> type, String name, boolean recursive) {
		if (isRootType(type)) {
			return null;
		}
		Field[] fields = type.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(name)) {
				return new ObjectField(field);
			}
		}
		if (!recursive) {
			return null;
		}
		return findField(type.getSuperclass(), name, recursive);
	}

	public static boolean isRootType(Class<?> type) {
		if (type == null) {
			return true;
		}
		if (type.equals(Object.class)) {
			return true;
		}
		if (type.equals(Class.class)) {
			return true;
		}
		if (type.equals(Thread.class)) {
			return true;
		}
		return false;
	}

	private Fields() {
	}

	public static boolean isStatic(Field field) {
		return Modifier.isStatic(field.getModifiers());
	}

}
