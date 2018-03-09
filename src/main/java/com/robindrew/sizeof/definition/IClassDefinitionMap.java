package com.robindrew.sizeof.definition;

import java.lang.reflect.Field;

public interface IClassDefinitionMap {

	<T> void defineType(Class<T> clazz, IClassDefinition<T> definition);

	<T> void ignoreType(Class<T> type);

	void ignoreField(Field field);

	boolean shouldIgnoreField(Field field);

	void ignoreField(Class<?> type, String fieldName);

	void ignoreField(String className, String fieldName);

	<T> IClassDefinition<T> get(Class<T> clazz);

	boolean contains(Class<?> clazz);

}
