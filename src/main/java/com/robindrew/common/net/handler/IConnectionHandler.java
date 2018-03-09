package com.robindrew.common.net.handler;

import com.robindrew.common.net.connection.IConnection;

public interface IConnectionHandler extends Runnable {

	IConnection getConnection();

	long getNumber();

}
