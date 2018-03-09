package com.robindrew.common.net.connection;

import java.net.SocketAddress;

public interface INetworkAddress {

	String getHostname();

	String getAddress();

	int getPort();

	int getCode();

	SocketAddress toSocketAddress();

}
