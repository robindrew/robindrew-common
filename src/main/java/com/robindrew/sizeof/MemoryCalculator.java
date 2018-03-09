package com.robindrew.sizeof;

import java.util.Set;

import com.robindrew.sizeof.architecture.Architecture64Compressed;
import com.robindrew.sizeof.architecture.IArchitecture;
import com.robindrew.sizeof.definition.ClassDefinitionMap;
import com.robindrew.sizeof.definition.IClassDefinition;
import com.robindrew.sizeof.definition.IClassDefinitionMap;
import com.robindrew.sizeof.set.IdentityHashSet;

public class MemoryCalculator implements IMemoryCalculator {

	private final IArchitecture architecture;
	private IClassDefinitionMap definitionMap;
	private Set<Object> instanceSet;

	public MemoryCalculator(IArchitecture architecture, IClassDefinitionMap definitionMap, Set<Object> instanceSet) {
		if (architecture == null) {
			throw new NullPointerException("architecture");
		}
		if (definitionMap == null) {
			throw new NullPointerException("definitionMap");
		}
		this.definitionMap = definitionMap;
		this.instanceSet = instanceSet;
		this.architecture = architecture;
	}

	public MemoryCalculator(IArchitecture architecture) {
		if (architecture == null) {
			throw new NullPointerException("architecture");
		}
		this.architecture = architecture;
		this.definitionMap = new ClassDefinitionMap(architecture);
		this.instanceSet = new IdentityHashSet();
	}

	public MemoryCalculator() {
		this.architecture = new Architecture64Compressed();
		this.definitionMap = new ClassDefinitionMap(architecture);
		this.instanceSet = new IdentityHashSet();
	}

	@Override
	public IArchitecture getArchitecture() {
		return architecture;
	}

	@Override
	public IClassDefinitionMap getDefinitionMap() {
		return definitionMap;
	}

	@Override
	public Set<Object> getInstanceSet() {
		return instanceSet;
	}

	public void setDefinitionMap(IClassDefinitionMap definitionMap) {
		if (definitionMap == null) {
			throw new NullPointerException("definitionMap");
		}
		this.definitionMap = definitionMap;
	}

	public void setInstanceSet(Set<Object> set) {
		if (set == null) {
			throw new NullPointerException("set");
		}
		this.instanceSet = set;
	}

	/**
	 * Returns the approximate size (in bytes) of the given object.
	 * @param object the object.
	 * @return the size in bytes.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final <T> long sizeOf(T object) {
		if (object == null) {
			return 0;
		}
		if (instanceSet != null && !instanceSet.add(object)) {
			return 0;
		}

		Class<T> type = (Class<T>) object.getClass();
		IClassDefinition<T> definition = definitionMap.get(type);
		long size = definition.sizeOf(object, this);

		// Elements
		if (definition.hasElements(object)) {
			size = definition.sizeOfElements(object, this, size);
		}
		size = architecture.checkSize(size);

		// Done
		return size;
	}

}
