package com.robindrew.common.io;

public class NamedCloseableDeletage<C extends INamedCloseable> implements INamedCloseable {

	private final C delegate;

	public NamedCloseableDeletage(C delegate) {
		if (delegate == null) {
			throw new NullPointerException("delegate");
		}
		this.delegate = delegate;
	}

	@Override
	public String getName() {
		return delegate.getName();
	}

	public C getDelegate() {
		return delegate;
	}

	@Override
	public void close() {
		delegate.close();
	}

}
