package com.robindrew.common.lang.clazz;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A Class Utility.
 */
public class Classes {

	public static final List<Class<?>> isAssignableFrom(Class<?> from, Collection<? extends Class<?>> classes) {
		List<Class<?>> list = new ArrayList<>();
		for (Class<?> clazz : classes) {
			if (from.isAssignableFrom(clazz)) {
				list.add(clazz);
			}
		}
		return list;
	}

	/**
	 * Returns the class for the given name.
	 * @param className the class name.
	 * @return the class for the given name.
	 */
	public static final Class<?> forName(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("className: '" + className + "'", e);
		}
	}

	/**
	 * Returns a new instance of the class.
	 * @param clazz the class.
	 * @return a new instance of the class.
	 */
	public static final <T> T newInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("class: " + clazz, e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException("class: " + clazz, e);
		}
	}

	/**
	 * Returns a new instance of the named class.
	 * @param className the class name.
	 * @return a new instance of the namedclass.
	 */
	public static final Object newInstance(String className) {
		Class<?> clazz = forName(className);
		if (clazz.isInterface()) {
			throw new IllegalArgumentException("Class is interface: " + clazz);
		}
		if (clazz.isPrimitive()) {
			throw new IllegalArgumentException("Class is primitive: " + clazz);
		}
		if (Modifier.isAbstract(clazz.getModifiers())) {
			throw new IllegalArgumentException("Class is abstract: " + clazz);
		}
		return newInstance(clazz);
	}
}
