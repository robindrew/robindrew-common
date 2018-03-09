package com.robindrew.common.dependency.lazy;

public abstract class LazyInstance<I> implements ILazyInstance<I> {

	private volatile I instance = null;

	@Override
	public I initialize() {
		if (instance != null) {
			return instance;
		}

		synchronized (this) {
			if (instance == null) {
				instance = newInstance();
				if (instance == null) {
					throw new IllegalStateException("initialized to null");
				}
			}
			return instance;
		}
	}

	protected abstract I newInstance();

}
