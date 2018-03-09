package com.robindrew.common.lang.reflect.method;

import java.util.List;

public interface IMethod {

	String getName();

	boolean isStatic();

	boolean isFinal();

	boolean isSetter();

	boolean isGetter();

	String getFieldName();

	Class<?> getReturnType();

	List<Class<?>> getParameterTypes();

	Object invoke(Object instance, Object... parameters);

}
