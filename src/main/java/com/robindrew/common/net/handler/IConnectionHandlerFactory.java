package com.robindrew.common.net.handler;

import com.robindrew.common.net.connection.IConnection;

public interface IConnectionHandlerFactory {

	IConnectionHandler newConnection(IConnection connection, long number);

}
