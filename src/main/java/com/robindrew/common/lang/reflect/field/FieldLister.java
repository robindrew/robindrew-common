package com.robindrew.common.lang.reflect.field;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FieldLister implements IFieldLister {

	private boolean recursive = true;
	private boolean setAccessible = true;
	private boolean includeStatic = false;
	private boolean includeFinal = false;
	private final Set<Class<? extends Annotation>> includeAnnotations = new HashSet<Class<? extends Annotation>>();

	@Override
	public IFieldLister includeAnnotation(Class<? extends Annotation> include) {
		if (include == null) {
			throw new NullPointerException("include");
		}
		this.includeAnnotations.add(include);
		return this;
	}

	@Override
	public IFieldLister setAccessible(boolean accessible) {
		this.setAccessible = accessible;
		return this;
	}

	@Override
	public IFieldLister includeStatic(boolean include) {
		this.includeStatic = include;
		return this;
	}

	@Override
	public IFieldLister includeFinal(boolean include) {
		this.includeFinal = include;
		return this;
	}

	@Override
	public IFieldLister setRecursive(boolean recursive) {
		this.recursive = recursive;
		return this;
	}

	@Override
	public final List<IField> getFieldList(Class<?> type) {
		if (type == null) {
			throw new NullPointerException();
		}
		List<IField> list = new ArrayList<IField>();
		addFields(type, list);
		return list;
	}

	private final void addFields(Class<?> type, List<IField> list) {
		if (Fields.isRootType(type)) {
			return;
		}

		Field[] fields = type.getDeclaredFields();
		for (Field field : fields) {
			addField(new ObjectField(field), list);
		}

		// Recursively list?
		if (recursive) {
			addFields(type.getSuperclass(), list);
		}
	}

	private boolean addField(IField field, List<IField> list) {

		// Ignore Static Fields?
		if (!includeStatic && field.isStatic()) {
			return false;
		}

		// Ignore Final Fields?
		if (!includeFinal && field.isFinal()) {
			return false;
		}

		// Only specific annotations?
		if (!field.hasAnnotations(includeAnnotations)) {
			return false;
		}

		// Set accessible?
		if (setAccessible) {
			field.setAccessible(true);
		}

		// Add!
		list.add(field);
		return true;
	}

}
