package com.robindrew.common.lang.clazz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A Class Resolver
 */
public class ClassResolver implements IClassResolver {

	private static final Map<String, Class<?>> nameToPrimitiveMap = new HashMap<String, Class<?>>();

	static {
		nameToPrimitiveMap.put(boolean.class.getName(), boolean.class);
		nameToPrimitiveMap.put(byte.class.getName(), byte.class);
		nameToPrimitiveMap.put(short.class.getName(), short.class);
		nameToPrimitiveMap.put(int.class.getName(), int.class);
		nameToPrimitiveMap.put(long.class.getName(), long.class);
		nameToPrimitiveMap.put(float.class.getName(), float.class);
		nameToPrimitiveMap.put(double.class.getName(), double.class);
		nameToPrimitiveMap.put(char.class.getName(), char.class);
		nameToPrimitiveMap.put("String", String.class);
		nameToPrimitiveMap.put("Class", Class.class);
		nameToPrimitiveMap.put("void", Void.class);
	}

	private final boolean ignoreCase;
	private final Map<String, Object> nameToClassMap = new HashMap<String, Object>();

	public ClassResolver(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public ClassResolver() {
		this(false);
	}

	@Override
	public void addClasses(Map<String, Class<?>> map) {
		for (Entry<String, Class<?>> entry : map.entrySet()) {
			addClass(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void addClassNames(Map<String, String> map) {
		for (Entry<String, String> entry : map.entrySet()) {
			addClass(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public <C extends Collection<Class<?>>> void addClasses(C classes) {
		for (Class<?> element : classes) {
			addClass(element.getSimpleName(), element);
		}
	}

	@Override
	public void addClass(String name, Class<?> element) {
		if (ignoreCase) {
			name = name.toLowerCase();
		}
		nameToClassMap.put(name, element);
	}

	@Override
	public void addClass(String name, String className) {
		if (ignoreCase) {
			name = name.toLowerCase();
		}
		nameToClassMap.put(name, className);
	}

	@Override
	public Class<?> resolveClass(String className) {
		if (className == null) {
			throw new NullPointerException("className");
		}
		if (className.length() == 0) {
			throw new IllegalArgumentException("className is empty");
		}
		if (ignoreCase) {
			className = className.toLowerCase();
		}
		Class<?> clazz = nameToPrimitiveMap.get(className);
		if (clazz != null) {
			return clazz;
		}
		Object object = nameToClassMap.get(className);
		if (object != null) {
			if (object instanceof Class) {
				return (Class<?>) object;
			}
			className = (String) object;
		}
		return Classes.forName(className);
	}

	@Override
	public List<Class<?>> resolveClasses(String... classNames) {
		if (classNames == null) {
			throw new NullPointerException();
		}
		List<Class<?>> classes = new ArrayList<Class<?>>(classNames.length);
		for (String className : classNames) {
			classes.add(resolveClass(className));
		}
		return classes;
	}

}
