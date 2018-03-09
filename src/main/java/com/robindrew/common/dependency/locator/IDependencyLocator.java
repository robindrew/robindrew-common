package com.robindrew.common.dependency.locator;

import java.util.List;

import com.robindrew.common.dependency.instance.IInstanceFactory;
import com.robindrew.common.dependency.lazy.ILazyInstance;

public interface IDependencyLocator {

	String getName();

	<C, I extends C> I set(Class<C> key, I instance);

	<C, I extends C> void setLazyInstance(Class<C> key, ILazyInstance<I> instance);

	<C, I extends C> void setInstanceFactory(Class<C> key, IInstanceFactory<I> instance);

	<C, I extends C> I get(Class<C> key);

	<C, I extends C> I get(Class<C> key, I defaultInstance);

	<C, I extends C> I getInstance(Class<C> key, Object... args);

	<C> void clear(Class<C> key);

	List<Object> getAll();

	boolean remove(Object key);

	int size();

	boolean isEmpty();

	void clear();

}
