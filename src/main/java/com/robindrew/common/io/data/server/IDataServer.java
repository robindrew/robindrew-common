package com.robindrew.common.io.data.server;

public interface IDataServer extends Runnable, AutoCloseable {

	IDataConnectionHandler getHandler();

	boolean isClosed();

	long getConnectionCount();
	
}
