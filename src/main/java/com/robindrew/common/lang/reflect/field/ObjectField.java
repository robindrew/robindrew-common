package com.robindrew.common.lang.reflect.field;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

import com.robindrew.common.util.Java;

public class ObjectField implements IField {

	public static final IField[] getObjectFields(Class<?> type) {
		Field[] declared = type.getDeclaredFields();
		IField[] fields = new ObjectField[declared.length];
		for (int i = 0; i < declared.length; i++) {
			fields[i] = new ObjectField(declared[i]);
		}
		return fields;
	}

	private final Field field;

	public ObjectField(Field field) {
		if (field == null) {
			throw new NullPointerException("field");
		}
		this.field = field;
	}

	public ObjectField(IField field) {
		this(field.getField());
	}

	@Override
	public Field getField() {
		return field;
	}

	@Override
	public String toString() {
		return field.toString();
	}

	@Override
	public String getName() {
		return field.getName();
	}

	@Override
	public Class<?> getType() {
		return field.getType();
	}

	@Override
	public boolean isStatic() {
		return Modifier.isStatic(field.getModifiers());
	}

	@Override
	public boolean isFinal() {
		return Modifier.isFinal(field.getModifiers());
	}

	@Override
	public boolean hasAnnotations(Set<Class<? extends Annotation>> annotationSet) {
		if (annotationSet.isEmpty()) {
			return true;
		}
		Annotation[] annotations = field.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotationSet.contains(annotation.annotationType())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isAccessible() {
		return field.isAccessible();
	}

	@Override
	public void setAccessible(boolean accessible) {
		if (isAccessible() != accessible) {
			field.setAccessible(accessible);
		}
	}

	@Override
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		return field.getAnnotation(annotationClass);
	}

	@Override
	public void set(Object instance, Object value) {
		try {
			setAccessible(true);
			field.set(instance, value);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public void setByte(Object instance, byte value) {
		try {
			setAccessible(true);
			field.setByte(instance, value);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public void setShort(Object instance, short value) {
		try {
			setAccessible(true);
			field.setShort(instance, value);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public void setInt(Object instance, int value) {
		try {
			setAccessible(true);
			field.setInt(instance, value);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public void setLong(Object instance, long value) {
		try {
			setAccessible(true);
			field.setLong(instance, value);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public void setFloat(Object instance, float value) {
		try {
			setAccessible(true);
			field.setFloat(instance, value);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public void setDouble(Object instance, double value) {
		try {
			setAccessible(true);
			field.setDouble(instance, value);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public void setChar(Object instance, char value) {
		try {
			setAccessible(true);
			field.setChar(instance, value);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public void setBoolean(Object instance, boolean value) {
		try {
			setAccessible(true);
			field.setBoolean(instance, value);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public Object get(Object instance) {
		try {
			setAccessible(true);
			return field.get(instance);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public byte getByte(Object instance) {
		try {
			setAccessible(true);
			return field.getByte(instance);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public short getShort(Object instance) {
		try {
			setAccessible(true);
			return field.getShort(instance);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public int getInt(Object instance) {
		try {
			setAccessible(true);
			return field.getInt(instance);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public long getLong(Object instance) {
		try {
			setAccessible(true);
			return field.getLong(instance);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public float getFloat(Object instance) {
		try {
			setAccessible(true);
			return field.getFloat(instance);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public double getDouble(Object instance) {
		try {
			setAccessible(true);
			return field.getDouble(instance);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public char getChar(Object instance) {
		try {
			setAccessible(true);
			return field.getChar(instance);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public boolean getBoolean(Object instance) {
		try {
			setAccessible(true);
			return field.getBoolean(instance);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}
}
