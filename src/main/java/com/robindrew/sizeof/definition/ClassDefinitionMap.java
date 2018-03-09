package com.robindrew.sizeof.definition;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import com.robindrew.sizeof.SizeOfException;
import com.robindrew.sizeof.architecture.IArchitecture;
import com.robindrew.sizeof.definition.type.BooleanArrayDefinition;
import com.robindrew.sizeof.definition.type.ByteArrayDefinition;
import com.robindrew.sizeof.definition.type.CharArrayDefinition;
import com.robindrew.sizeof.definition.type.DoubleArrayDefinition;
import com.robindrew.sizeof.definition.type.FloatArrayDefinition;
import com.robindrew.sizeof.definition.type.IgnoreDefinition;
import com.robindrew.sizeof.definition.type.IntArrayDefinition;
import com.robindrew.sizeof.definition.type.LongArrayDefinition;
import com.robindrew.sizeof.definition.type.ObjectArrayDefinition;
import com.robindrew.sizeof.definition.type.ObjectDefinition;
import com.robindrew.sizeof.definition.type.ShortArrayDefinition;

/**
 * A Class Definition Map.
 */
public class ClassDefinitionMap implements IClassDefinitionMap {

	/** The class to definition map. */
	private final Map<Class<?>, IClassDefinition<?>> classToDefinition = new IdentityHashMap<Class<?>, IClassDefinition<?>>();
	/** The ignore field set. */
	private final Set<Field> ignoreFieldSet = new HashSet<Field>();
	/** The architecture. */
	private final IArchitecture architecture;

	/**
	 * Creates a new class cache.
	 */
	public ClassDefinitionMap(IArchitecture architecture) {
		if (architecture == null) {
			throw new NullPointerException("os");
		}
		this.architecture = architecture;
		try {
			defineRequiredTypes();
			defineOptionalTypes();
		} catch (Exception e) {
			throw new SizeOfException(e);
		}
	}

	/**
	 * Define the required base types necessary for any map.
	 */
	protected void defineRequiredTypes() throws Exception {
		defineType(Object[].class, new ObjectArrayDefinition());
		defineType(boolean[].class, new BooleanArrayDefinition());
		defineType(char[].class, new CharArrayDefinition());
		defineType(byte[].class, new ByteArrayDefinition());
		defineType(short[].class, new ShortArrayDefinition());
		defineType(int[].class, new IntArrayDefinition());
		defineType(long[].class, new LongArrayDefinition());
		defineType(float[].class, new FloatArrayDefinition());
		defineType(double[].class, new DoubleArrayDefinition());
		ignoreType(ClassLoader.class);
		ignoreType(Class.class);
	}

	/**
	 * Define optional types.
	 */
	protected void defineOptionalTypes() throws Exception {

		// StackOverflowError fix types
		// defineType(LinkedList.class, new LinkedListDefinition());
		// defineType(HashMap.class, new HashMapDefinition());
		// defineType(LinkedHashMap.class, new LinkedHashMapDefinition());
	}

	/**
	 * Set the definition for the given class.
	 * @param <T> the type.
	 * @param clazz the class.
	 * @param definition the definition.
	 */
	@Override
	public final <T> void defineType(Class<T> clazz, IClassDefinition<T> definition) {
		if (clazz == null || definition == null) {
			throw new NullPointerException();
		}
		classToDefinition.put(clazz, definition);
	}

	/**
	 * Ignore the given type (any instance will return zero).
	 * @param <T> the type.
	 * @param type the type.
	 */
	@Override
	public final <T> void ignoreType(Class<T> type) {
		defineType(type, new IgnoreDefinition<T>());
	}

	/**
	 * Ignore the given field.
	 * @param field the field.
	 */
	@Override
	public final void ignoreField(Field field) {
		if (field == null) {
			throw new NullPointerException();
		}
		ignoreFieldSet.add(field);
	}

	/**
	 * Returns true if the given field should be ignored.
	 * @param field the field.
	 * @return true if the given field should be ignored.
	 */
	@Override
	public final boolean shouldIgnoreField(Field field) {
		return ignoreFieldSet.contains(field);
	}

	/**
	 * Ignore the named field in the given class.
	 * @param type the class.
	 * @param fieldName the field name.
	 */
	@Override
	public final void ignoreField(Class<?> type, String fieldName) {
		final Field field;
		try {
			field = type.getDeclaredField(fieldName);
		} catch (Exception e) {
			throw new SizeOfException(e);
		}
		ignoreField(field);
	}

	/**
	 * Ignore the named field in the given class.
	 * @param className the class name.
	 * @param fieldName the field name.
	 */
	@Override
	public final void ignoreField(String className, String fieldName) {
		final Class<?> clazz;
		try {
			clazz = Class.forName(className);
		} catch (Exception e) {
			throw new SizeOfException(e);
		}
		ignoreField(clazz, fieldName);
	}

	/**
	 * Returns the definition for the given class.
	 * @param <T> the type.
	 * @param clazz the class.
	 * @return the definition.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final <T> IClassDefinition<T> get(Class<T> clazz) {
		IClassDefinition<T> definition = (IClassDefinition<T>) classToDefinition.get(clazz);
		if (definition == null) {
			definition = newDefinition(clazz);
			classToDefinition.put(clazz, definition);
		}
		return definition;
	}

	/**
	 * Returns true if this contains a definition for the given class.
	 * @param clazz the class.
	 * @return true if a definition exists.
	 */
	@Override
	public final boolean contains(Class<?> clazz) {
		return classToDefinition.containsKey(clazz);
	}

	/**
	 * Returns a new definition for the given class.
	 * @param <T> the type.
	 * @param clazz the class.
	 * @return the definition.
	 */
	@SuppressWarnings("unchecked")
	protected <T> IClassDefinition<T> newDefinition(Class<T> clazz) {
		IClassDefinition<T> definition;
		if (Object[].class.isAssignableFrom(clazz)) {
			definition = (IClassDefinition<T>) new ObjectArrayDefinition();
		} else {
			definition = new ObjectDefinition<T>(clazz, this, architecture);
		}
		return definition;
	}
}
