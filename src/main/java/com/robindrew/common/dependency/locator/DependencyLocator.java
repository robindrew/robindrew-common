package com.robindrew.common.dependency.locator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.collect.copyonwrite.CopyOnWriteMap;
import com.robindrew.common.dependency.instance.IInstanceFactory;
import com.robindrew.common.dependency.lazy.ILazyInstance;

public class DependencyLocator implements IDependencyLocator {

	private static final Logger log = LoggerFactory.getLogger(DependencyLocator.class);

	public static final DependencyLocator GLOBAL = new DependencyLocator("GLOBAL");

	/** The interface mapping! */
	private final Map<Object, Object> interfaceToObjectMap = new CopyOnWriteMap<Object, Object>();

	/** The locator name (for debugging). */
	private final String name;
	/** The optional delegate factory, can be NULL. */
	private final IDependencyLocator delegate;
	/** Strict mode forces keys to be interfaces. */
	private volatile boolean strictMode = false;

	public DependencyLocator(String name, IDependencyLocator delegate) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		if (delegate == null) {
			throw new NullPointerException("delegate");
		}
		this.name = name;
		this.delegate = delegate;
	}

	private DependencyLocator(String name) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		this.name = name;
		this.delegate = null;
	}

	@Override
	public String getName() {
		return name;
	}

	public boolean isStrictMode() {
		return strictMode;
	}

	public void setStrictMode(boolean enabled) {
		this.strictMode = enabled;
	}

	protected Map<Object, Object> getInterfaceToObjectMap() {
		return interfaceToObjectMap;
	}

	protected void checkKey(Class<?> key) {
		if (!key.isInterface() && isStrictMode()) {
			throw new IllegalStateException("key must be an interface (strict mode)");
		}
	}

	protected void checkCompatibility(Class<?> key, Object instance) {
		if (!key.isAssignableFrom(instance.getClass())) {
			throw new IllegalStateException("Dependency key not assignable from instance: '" + key + "' -> '" + instance.getClass() + "'");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C, I extends C> I get(Class<C> key) {
		if (key == null) {
			throw new NullPointerException("key");
		}

		I instance = (I) getInterfaceToObjectMap().get(key);
		if (instance == null) {

			// Not found? Delegate ...
			if (delegate != null) {
				return (I) delegate.get(key);
			}
			throw new IllegalArgumentException("Dependency not assigned: '" + key + "' in DependencyLocator[" + getName() + "]");
		}

		// Handle lazy initialization
		if (instance instanceof ILazyInstance) {
			instance = lazilyInitialize(key);
		}

		// Handle instance creation
		if (instance instanceof IInstanceFactory) {
			IInstanceFactory<I> factory = (IInstanceFactory<I>) instance;
			return factory.newInstance();
		}
		return instance;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C, I extends C> I getInstance(Class<C> key, Object... args) {
		if (key == null) {
			throw new NullPointerException("key");
		}

		I instance = (I) getInterfaceToObjectMap().get(key);
		if (instance == null) {

			// Not found? Delegate ...
			if (delegate != null) {
				return (I) delegate.getInstance(key);
			}
			throw new IllegalArgumentException("Dependency not assigned: '" + key + "' in " + getInterfaceToObjectMap());
		}

		// This method assumes a factory is used
		if (!(instance instanceof IInstanceFactory)) {
			throw new IllegalArgumentException("Dependency not a factory: '" + key + "' in " + getInterfaceToObjectMap());
		}

		// Handle instance creation
		IInstanceFactory<I> factory = (IInstanceFactory<I>) instance;
		return factory.newInstance(args);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C, I extends C> I get(Class<C> key, I defaultInstance) {
		if (key == null) {
			throw new NullPointerException("key");
		}

		I instance = (I) getInterfaceToObjectMap().get(key);
		if (instance == null) {

			// Not found? Delegate ...
			if (delegate != null) {
				return delegate.get(key, defaultInstance);
			}
			return defaultInstance;
		}

		// Handle instance creation
		if (instance instanceof IInstanceFactory) {
			IInstanceFactory<I> factory = (IInstanceFactory<I>) instance;
			return factory.newInstance();
		}
		return instance;
	}

	@Override
	public <C> void clear(Class<C> key) {
		if (key == null) {
			throw new NullPointerException("key");
		}
		getInterfaceToObjectMap().remove(key);
		if (delegate != null) {
			delegate.clear(key);
		}
	}

	@Override
	public <C, I extends C> I set(Class<C> key, I instance) {
		if (key == null) {
			throw new NullPointerException("key");
		}
		if (instance == null) {
			throw new NullPointerException("instance");
		}
		checkKey(key);

		if (getInterfaceToObjectMap().put(key, instance) == null) {
			if (log.isDebugEnabled()) {
				log.debug("Registered Dependency #" + getInterfaceToObjectMap().size() + ": '" + key + "' -> " + instance);
			}
		} else {
			log.warn("Replaced Dependency: '" + key + "' -> " + instance);
		}
		return instance;
	}

	@Override
	public <C, I extends C> void setLazyInstance(Class<C> key, ILazyInstance<I> instance) {
		if (key == null) {
			throw new NullPointerException("key");
		}
		if (instance == null) {
			throw new NullPointerException("instance");
		}
		checkKey(key);

		if (getInterfaceToObjectMap().put(key, instance) == null) {
			if (log.isDebugEnabled()) {
				log.debug("Registered Dependency (Lazy): '" + key + "' -> " + instance);
			}
		} else {
			log.warn("Replaced Dependency (Lazy): '" + key + "' -> " + instance);
		}
	}

	@SuppressWarnings("unchecked")
	public <C, I extends C> I lazilyInitialize(Class<C> key) {

		// We synchronize to guarantee initialization occurs only once ...
		// regardless of the implementation of ILazyInstance.initialize()
		synchronized (key) {

			Object instance = getInterfaceToObjectMap().get(key);
			if (instance instanceof ILazyInstance) {

				// Lazy!
				ILazyInstance<I> lazy = (ILazyInstance<I>) instance;
				instance = lazy.initialize();

				// NULL is evil ...
				if (instance == null) {
					throw new IllegalStateException("Dependency lazily initialized to null: '" + key + "' in " + getInterfaceToObjectMap());
				}

				// We don't support chained lazy initialization!
				if (instance instanceof ILazyInstance) {
					throw new IllegalStateException("Dependency lazily initialized to another ILazyInstance: '" + key + "' in " + getInterfaceToObjectMap());
				}

				// Sanity check!
				checkCompatibility(key, instance);

				// Replace the lazy instance with the initialized version
				getInterfaceToObjectMap().put(key, instance);
			}

			// Done
			return (I) instance;
		}
	}

	@Override
	public <C, I extends C> void setInstanceFactory(Class<C> key, IInstanceFactory<I> factory) {
		if (key == null) {
			throw new NullPointerException("key");
		}
		if (factory == null) {
			throw new NullPointerException("factory");
		}
		checkKey(key);

		if (getInterfaceToObjectMap().put(key, factory) == null) {
			if (log.isDebugEnabled()) {
				log.debug("Registered Dependency (Factory): '" + key + "' -> " + factory);
			}
		} else {
			log.warn("Replaced Dependency (Factory): '" + key + "' -> " + factory);
		}
	}

	@Override
	public List<Object> getAll() {
		return new ArrayList<Object>(interfaceToObjectMap.values());
	}

	@Override
	public boolean remove(Object key) {
		return interfaceToObjectMap.remove(key) != null;
	}

	@Override
	public int size() {
		return interfaceToObjectMap.size();
	}

	@Override
	public boolean isEmpty() {
		return interfaceToObjectMap.isEmpty();
	}

	@Override
	public void clear() {
		if (log.isDebugEnabled()) {
			log.debug("Clearing Dependencies (" + interfaceToObjectMap.size() + ")");
		}
		interfaceToObjectMap.clear();
	}

	@Override
	public String toString() {
		return interfaceToObjectMap.toString();
	}

}
