package com.robindrew.common.io;

public interface INamedCloseable extends AutoCloseable {

	default String getName() {
		return getClass().getSimpleName();
	}

	@Override
	default void close() {
	}

}
