package com.robindrew.common.lang.reflect.field;

import java.lang.reflect.Field;

public class InstanceField extends ObjectField implements IInstanceField {

	public static final IInstanceField[] getInstanceFields(Object instance) {
		if (instance instanceof Class) {
			throw new IllegalArgumentException("Class: " + instance);
		}
		Class<? extends Object> type = instance.getClass();
		Field[] declared = type.getDeclaredFields();
		IInstanceField[] fields = new IInstanceField[declared.length];
		for (int i = 0; i < declared.length; i++) {
			fields[i] = new InstanceField(declared[i], instance);
		}
		return fields;
	}

	private final Object instance;

	public InstanceField(Field field, Object instance) {
		super(field);
		if (instance == null) {
			throw new NullPointerException("instance");
		}
		this.instance = instance;
	}

	public InstanceField(IField field, Object instance) {
		super(field);
		if (instance == null) {
			throw new NullPointerException("instance");
		}
		this.instance = instance;
	}

	@Override
	public void setValue(Object value) {
		super.set(instance, value);
	}

	@Override
	public Object getValue() {
		return super.get(instance);
	}

}
