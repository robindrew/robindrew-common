package com.robindrew.common.lang.reflect.method;

import static com.robindrew.common.lang.reflect.method.IMethodLister.Type.GETTER;
import static com.robindrew.common.lang.reflect.method.IMethodLister.Type.SETTER;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodLister implements IMethodLister {

	private boolean recursive = true;
	private Type type = Type.ALL;

	@Override
	public IMethodLister setRecursive(boolean recursive) {
		this.recursive = recursive;
		return this;
	}

	@Override
	public IMethodLister setType(Type type) {
		if (type == null) {
			throw new NullPointerException("type");
		}
		this.type = type;
		return this;
	}

	@Override
	public final List<IMethod> getMethodList(Class<?> type) {
		if (type == null) {
			throw new NullPointerException();
		}
		List<IMethod> list = new ArrayList<IMethod>();
		addMethods(type, list);
		return list;
	}

	private final void addMethods(Class<?> type, List<IMethod> list) {
		if (Methods.isRootType(type)) {
			return;
		}

		Method[] fields = type.getDeclaredMethods();
		for (Method field : fields) {
			addMethod(new ObjectMethod(field), list);
		}

		// Recursively list?
		if (recursive) {
			addMethods(type.getSuperclass(), list);
		}
	}

	private boolean addMethod(IMethod field, List<IMethod> list) {
		if (type.equals(SETTER) && !field.isSetter()) {
			return false;
		}
		if (type.equals(GETTER) && !field.isGetter()) {
			return false;
		}

		// Add!
		list.add(field);
		return true;
	}

}
