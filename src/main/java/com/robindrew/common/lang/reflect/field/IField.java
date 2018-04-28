package com.robindrew.common.lang.reflect.field;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

public interface IField {

	Field getField();

	String getName();

	Class<?> getType();

	boolean isStatic();

	boolean isFinal();

	void set(Object instance, Object value);

	void setByte(Object instance, byte value);

	void setShort(Object instance, short value);

	void setInt(Object instance, int value);

	void setLong(Object instance, long value);

	void setFloat(Object instance, float value);

	void setDouble(Object instance, double value);

	void setChar(Object instance, char value);

	void setBoolean(Object instance, boolean value);

	<V> V get(Object instance);

	byte getByte(Object instance);

	short getShort(Object instance);

	int getInt(Object instance);

	long getLong(Object instance);

	float getFloat(Object instance);

	double getDouble(Object instance);

	char getChar(Object instance);

	boolean getBoolean(Object instance);

	boolean hasAnnotations(Set<Class<? extends Annotation>> includeAnnotations);

	boolean isAccessible();

	void setAccessible(boolean accessible);

	<A extends Annotation> A getAnnotation(Class<A> annotationClass);

	boolean isType(Class<?> type);

}
