package com.robindrew.common.lang.reflect.method;

import static java.lang.Character.toLowerCase;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import com.robindrew.common.util.Java;

public class ObjectMethod implements IMethod {

	public static final IMethod[] getObjectMethods(Class<?> type) {
		Method[] declared = type.getDeclaredMethods();
		IMethod[] fields = new ObjectMethod[declared.length];
		for (int i = 0; i < declared.length; i++) {
			fields[i] = new ObjectMethod(declared[i]);
		}
		return fields;
	}

	private final Method method;

	public ObjectMethod(Method method) {
		if (method == null) {
			throw new NullPointerException("field");
		}
		this.method = method;
	}

	@Override
	public String toString() {
		return method.toString();
	}

	@Override
	public String getName() {
		return method.getName();
	}

	@Override
	public boolean isStatic() {
		return Modifier.isStatic(method.getModifiers());
	}

	@Override
	public boolean isFinal() {
		return Modifier.isFinal(method.getModifiers());
	}

	@Override
	public Class<?> getReturnType() {
		return method.getReturnType();
	}

	@Override
	public List<Class<?>> getParameterTypes() {
		return Arrays.asList(method.getParameterTypes());
	}

	@Override
	public boolean isSetter() {
		String name = getName();
		if (name.length() <= 3) {
			return false;
		}
		if (!name.startsWith("set")) {
			return false;
		}
		if (!Character.isUpperCase(name.charAt(3))) {
			return false;
		}
		if (method.getParameterTypes().length != 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isGetter() {
		String name = getName();
		if (name.length() <= 2) {
			return false;
		}
		if (!name.startsWith("get") && !name.startsWith("is")) {
			return false;
		}
		if (method.getParameterTypes().length > 0) {
			return false;
		}
		if (method.getReturnType().equals(Void.TYPE)) {
			return false;
		}
		return true;
	}

	@Override
	public String getFieldName() {
		String name = getName();
		if (!isGetter() && !isSetter()) {
			throw new IllegalStateException("Method must be a getter or setter");
		}
		if (name.startsWith("is")) {
			name = name.substring(2);
		} else {
			name = name.substring(3);
		}
		return toLowerCase(name.charAt(0)) + name.substring(1);
	}

	@Override
	public Object invoke(Object instance, Object... parameters) {
		try {
			return method.invoke(instance, parameters);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

}
