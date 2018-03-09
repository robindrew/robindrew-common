package com.robindrew.common.dependency;

import com.robindrew.common.dependency.instance.IInstanceFactory;
import com.robindrew.common.dependency.locator.DependencyLocator;
import com.robindrew.common.dependency.locator.IDependencyLocator;

@SuppressWarnings("unchecked")
public class DependencyFactory {

	private static volatile IDependencyLocator globalFactory = DependencyLocator.GLOBAL;
	private static volatile ThreadLocal<IDependencyLocator> localFactories = new ThreadLocal<IDependencyLocator>();

	public static void clearDependencies() {
		globalFactory.clear();
		localFactories = new ThreadLocal<IDependencyLocator>();
	}

	public static IDependencyLocator getInstance() {
		IDependencyLocator localFactory = localFactories.get();
		if (localFactory != null) {
			return localFactory;
		}
		return globalFactory;
	}

	public static IDependencyLocator getLocalInstance() {
		return localFactories.get();
	}

	public static IDependencyLocator getGlobalInstance() {
		return globalFactory;
	}

	public static void setGlobalInstance(IDependencyLocator factory) {
		// There must always be a global instance!
		if (factory == null) {
			throw new NullPointerException();
		}
		globalFactory = factory;
	}

	public static void setLocalInstance(IDependencyLocator factory) {
		// Set to null to remove the local instance if required
		localFactories.set(factory);
	}

	public static <C> void clearDependency(Class<C> clazz) {
		getInstance().clear(clazz);
	}

	public static <C, I extends C> I getDependency(Class<C> clazz) {
		return (I) getInstance().get(clazz);
	}

	public static <C, I extends C> I getDependency(Class<C> clazz, I defaultInstance) {
		return getInstance().get(clazz, defaultInstance);
	}

	public static <C, I extends C> I setDependency(Class<C> clazz, I instance) {
		return getInstance().set(clazz, instance);
	}

	public static <C, I extends C> I getDependencyInstance(Class<C> clazz, Object... args) {
		return getInstance().getInstance(clazz, args);
	}

	public static <C, I extends C> void setDependencyInstanceFactory(Class<C> clazz, IInstanceFactory<I> factory) {
		getInstance().setInstanceFactory(clazz, factory);
	}
}
