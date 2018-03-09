package com.robindrew.common.net.connection;

import java.io.InputStream;
import java.io.OutputStream;

public interface IConnection extends AutoCloseable {

	INetworkAddress getLocalAddress();

	INetworkAddress getRemoteAddress();

	InputStream getInput();

	OutputStream getOutput();

	boolean isConnected();

	void disconnect();

}
