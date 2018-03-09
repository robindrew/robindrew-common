package com.robindrew.common.net.connection;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.robindrew.common.util.Java;

public class NetworkAddress implements INetworkAddress {

	public static final int MAXIMUM_PORT = 65535;

	private final InetSocketAddress address;

	public NetworkAddress(SocketAddress address) {
		if (address == null) {
			throw new NullPointerException("address");
		}
		this.address = (InetSocketAddress) address;
		this.address.getAddress();
	}

	public NetworkAddress(InetAddress address, int port) {
		this(new InetSocketAddress(address, port));
	}

	public NetworkAddress(int port) {
		this(Java.getHostName(), port);
	}

	public NetworkAddress(String host, int port) {
		this(new InetSocketAddress(host, port));
	}

	@Override
	public String getHostname() {
		return address.getAddress().getHostName();
	}

	@Override
	public String getAddress() {
		return address.getAddress().getHostAddress();
	}

	@Override
	public int getPort() {
		return address.getPort();
	}

	@Override
	public String toString() {
		return address.toString();
	}

	@Override
	public SocketAddress toSocketAddress() {
		return address;
	}

	@Override
	public int getCode() {
		return address.getAddress().hashCode();
	}

}
