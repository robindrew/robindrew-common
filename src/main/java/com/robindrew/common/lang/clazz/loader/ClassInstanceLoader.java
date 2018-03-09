package com.robindrew.common.lang.clazz.loader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.lang.clazz.locator.IClassLocator;

public class ClassInstanceLoader extends ClassLoader {

	private static final Logger log = LoggerFactory.getLogger(ClassInstanceLoader.class);

	private final String name;
	private final Map<String, Class<?>> cache = new ConcurrentHashMap<>();
	private final IClassLocator locator;

	public ClassInstanceLoader(String name, IClassLocator locator) {
		// Setting the parent to null avoid simply delegating to the system class loader
		super(null);
		this.name = name;
		this.locator = locator;
	}

	public void register(Class<?> type) {
		cache.put(type.getName(), type);
	}

	public String getName() {
		return name;
	}

	@SuppressWarnings("unchecked")
	public <V> V newClassInstance(Class<?> type) throws Exception {
		Class<?> newType = loadClassFromType(type);
		return (V) newType.newInstance();
	}

	@SuppressWarnings("unchecked")
	public <V> V newClassInstance(String className) throws Exception {
		Class<?> newType = loadClass(className);
		return (V) newType.newInstance();
	}

	public Class<?> loadClassFromType(Class<?> type) throws Exception {
		return loadClass(type.getName());
	}

	@Override
	protected Class<?> findClass(String className) throws ClassNotFoundException {
		log.info("[{}] Loading Class: {}", getName(), className);

		// Check if the class is cached
		Class<?> found = cache.get(className);
		if (found != null) {
			log.info("[{}] Cached Class: {}", getName(), className);
			return cache.get(className);
		}

		// Locate the class
		byte[] bytes = locator.locateClass(className);
		found = defineClass(null, bytes, 0, bytes.length);
		if (found != null) {
			log.info("[{}] Defined Class: {}", getName(), className);
			cache.put(className, found);
			return found;
		}

		// Fallback on system class loader
		log.info("[{}] Loading Class: {} (using system class loader)", getName(), className);
		found = loadClassFromSystem(className);
		cache.put(className, found);
		return found;
	}

	protected Class<?> loadClassFromSystem(String name) throws ClassNotFoundException {
		ClassLoader loader = getClass().getClassLoader();
		return loader.loadClass(name);
	}
}
