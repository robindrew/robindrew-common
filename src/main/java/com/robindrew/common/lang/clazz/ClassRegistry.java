package com.robindrew.common.lang.clazz;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.robindrew.common.util.Java;

/**
 * A Class Registry.
 */
public class ClassRegistry<T> implements Iterable<Class<? extends T>> {

	/** The name to class map. */
	private final Map<String, Class<? extends T>> simpleNameToClassMap;

	/**
	 * Creates a new class registry.
	 */
	public ClassRegistry() {
		simpleNameToClassMap = new LinkedHashMap<String, Class<? extends T>>();
	}

	/**
	 * Register the given class.
	 * @param clazz the class.
	 */
	public void register(Class<? extends T> clazz) {
		synchronized (simpleNameToClassMap) {
			simpleNameToClassMap.put(clazz.getSimpleName().intern(), clazz);
		}
	}

	/**
	 * Populate the given map.
	 * @param map the map.
	 */
	public void populate(Map<String, Class<?>> map) {
		synchronized (simpleNameToClassMap) {
			map.putAll(simpleNameToClassMap);
		}
	}

	/**
	 * Returns this as a map.
	 * @return this as a map.
	 */
	public Map<String, Class<?>> toMap() {
		synchronized (simpleNameToClassMap) {
			return new LinkedHashMap<String, Class<?>>(simpleNameToClassMap);
		}
	}

	/**
	 * Returns a list of classes.
	 * @return a list of classes.
	 */
	public List<Class<? extends T>> getClassList() {
		synchronized (simpleNameToClassMap) {
			return new ArrayList<Class<? extends T>>(simpleNameToClassMap.values());
		}
	}

	/**
	 * Returns a list of instances.
	 * @return a list of instances.
	 */
	public List<T> getInstanceList() {
		List<T> instanceList = new ArrayList<T>();
		try {
			for (Class<? extends T> clazz : getClassList()) {
				T instance = clazz.newInstance();
				instanceList.add(instance);
			}
		} catch (Exception e) {
			throw Java.propagate(e);
		}
		return instanceList;
	}

	/**
	 * Returns an instance by simple name.
	 * @param simpleName the simple instance name.
	 * @return the instance.
	 */
	public T getInstance(String simpleName) {
		try {
			return getClass(simpleName).newInstance();
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	/**
	 * Returns a class by simple name.
	 * @param simpleName the simple class name.
	 * @return the class.
	 */
	public Class<? extends T> getClass(String simpleName) {
		synchronized (simpleNameToClassMap) {
			Class<? extends T> clazz = simpleNameToClassMap.get(simpleName);
			if (clazz == null) {
				throw new IllegalArgumentException("unknown class: '" + simpleName + "'");
			}
			return clazz;
		}
	}

	@SuppressWarnings("unchecked")
	public T newInstance(String simpleName) {
		try {
			Class<T> clazz = (Class<T>) getClass(simpleName);
			return clazz.newInstance();
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public Iterator<Class<? extends T>> iterator() {
		return getClassList().iterator();
	}

}
