package com.robindrew.common.io.data.server;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;

public interface IDataConnection extends AutoCloseable {

	@Override
	void close();
	
	boolean isClosed();

	IDataReader getReader();

	IDataWriter getWriter();
}
