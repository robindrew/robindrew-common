package com.robindrew.common.net.connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.util.Java;
import com.robindrew.common.util.Quietly;

public class SocketConnection implements IConnection {

	private static final Logger log = LoggerFactory.getLogger(SocketConnection.class);

	private final Socket socket;
	private final InputStream input;
	private final OutputStream output;
	private final AtomicBoolean connected = new AtomicBoolean(true);

	public SocketConnection(Socket socket) {
		if (socket == null) {
			throw new NullPointerException("socket");
		}
		this.socket = socket;
		try {
			this.input = new BufferedInputStream(socket.getInputStream());
			this.output = new BufferedOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	public SocketConnection(String host, int port) {
		log.debug("[Connecting] " + host + ":" + port);
		try {
			this.socket = Sockets.connect(host, port);
			this.input = new BufferedInputStream(socket.getInputStream());
			this.output = new BufferedOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	public SocketConnection(INetworkAddress address) {
		this(address.getHostname(), address.getPort());
	}

	@Override
	public INetworkAddress getRemoteAddress() {
		return new NetworkAddress(socket.getRemoteSocketAddress());
	}

	@Override
	public INetworkAddress getLocalAddress() {
		return new NetworkAddress(socket.getLocalSocketAddress());
	}

	@Override
	public InputStream getInput() {
		return input;
	}

	@Override
	public OutputStream getOutput() {
		return output;
	}

	@Override
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public String toString() {
		return socket.toString();
	}

	@Override
	public boolean isConnected() {
		if (!socket.isConnected() || socket.isClosed()) {
			connected.set(false);
			return false;
		}
		return connected.get();
	}

	@Override
	public void disconnect() {
		connected.set(false);
		Quietly.close(socket);
	}

}
