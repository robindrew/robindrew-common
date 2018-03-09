package com.robindrew.common.lang.reflect.method;

import java.util.List;

public interface IMethodLister {

	public static enum Type {
		ALL, GETTER, SETTER;
	}

	IMethodLister setRecursive(boolean recursive);

	IMethodLister setType(Type type);

	List<IMethod> getMethodList(Class<?> type);

}
