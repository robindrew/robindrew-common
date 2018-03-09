package com.robindrew.common.net.connection;

public interface IConnector {

	boolean isConnected();

	IConnection getConnection();

	void disconnect();

}
