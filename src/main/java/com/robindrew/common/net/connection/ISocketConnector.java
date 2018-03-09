package com.robindrew.common.net.connection;

import java.net.Socket;
import java.net.SocketAddress;

public interface ISocketConnector {

	Socket connect(SocketAddress address, int connectTimeoutInMillis);

}
