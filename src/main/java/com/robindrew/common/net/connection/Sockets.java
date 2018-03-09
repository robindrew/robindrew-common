package com.robindrew.common.net.connection;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import org.eclipse.jetty.io.RuntimeIOException;

public final class Sockets {

	public static Socket connect(String host, int port) {
		try {
			return new Socket(host, port);
		} catch (Exception e) {
			throw new RuntimeIOException("Failed to connect to " + host + ":" + port, e);
		}
	}

	public static Socket connect(SocketAddress address, int connectTimeoutInMillis) {
		try {
			Socket socket = new Socket();
			socket.connect(address, connectTimeoutInMillis);
			return socket;
		} catch (Exception e) {
			throw new RuntimeIOException("Failed to connect to " + address, e);
		}
	}

	public static Socket connect(InetAddress address, int port) {
		try {
			return new Socket(address, port);
		} catch (Exception e) {
			throw new RuntimeIOException("Failed to connect to " + address + ":" + port, e);
		}
	}

	public static ServerSocket listen(int port) {
		try {
			return new ServerSocket(port);
		} catch (Exception e) {
			throw new RuntimeIOException("Failed to create a server listening on port " + port, e);
		}
	}

	private Sockets() {
	}

}
