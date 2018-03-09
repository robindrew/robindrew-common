package com.robindrew.common.lang.reflect.method;

import java.lang.reflect.Method;

public class Methods {

	public static final IMethod get(Class<?> type, String name, boolean recursive) {
		IMethod method = findMethod(type, name, recursive);
		if (method == null) {
			throw new IllegalArgumentException("Method " + name + " not found in " + type);
		}
		return method;
	}

	public static final IMethod get(Class<?> type, String name) {
		return get(type, name, true);
	}

	private static final IMethod findMethod(Class<?> type, String name, boolean recursive) {
		if (isRootType(type)) {
			return null;
		}
		Method[] methods = type.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals(name)) {
				return new ObjectMethod(method);
			}
		}
		if (!recursive) {
			return null;
		}
		return findMethod(type.getSuperclass(), name, recursive);
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

	private Methods() {
	}

}
