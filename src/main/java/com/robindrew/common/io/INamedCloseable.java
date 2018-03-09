package com.robindrew.common.io;

public interface INamedCloseable extends AutoCloseable {

	String getName();

	@Override
	void close();

}
