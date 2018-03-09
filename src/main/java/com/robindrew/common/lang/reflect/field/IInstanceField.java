package com.robindrew.common.lang.reflect.field;

public interface IInstanceField {

	String getName();

	Class<?> getType();

	boolean isStatic();

	boolean isFinal();

	void setValue(Object value);

	Object getValue();

}
