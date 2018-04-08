package com.robindrew.common.io.data.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.net.connection.Sockets;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Quietly;

public class DataServer implements IDataServer {

	private static final Logger log = LoggerFactory.getLogger(DataServer.class);

	private final String name;
	private final ServerSocket server;
	private final IDataConnectionHandler handler;
	private final AtomicBoolean closed = new AtomicBoolean(false);
	private final AtomicLong connectionCount = new AtomicLong(0);
	private final int port;

	public DataServer(String name, int port, IDataConnectionHandler handler) {
		this.name = Check.notEmpty("name", name);
		this.port = port;
		this.server = Sockets.listen(port);
		this.handler = Check.notNull("handler", handler);

		log.info("[{}] listening on port {}", name, port);
	}

	public int getPort() {
		return port;
	}

	public String getName() {
		return name;
	}

	public Thread start() {
		Thread thread = new Thread(this, getName());
		thread.start();
		return thread;
	}

	@Override
	public IDataConnectionHandler getHandler() {
		return handler;
	}

	@Override
	public boolean isClosed() {
		return closed.get() || server.isClosed();
	}

	@Override
	public void run() {
		log.info("[{}] accepting connections");

		try {
			while (!isClosed()) {
				Socket socket = server.accept();
				connectionCount.incrementAndGet();
				IDataConnection connection = new DataConnection(socket);
				handler.newConnection(connection);
			}
		} catch (Throwable t) {
			log.error("Error running server", t);
		} finally {
			close();
		}

		log.info("[{}] closed");
	}

	@Override
	public void close() {
		closed.set(true);
		Quietly.close(server);
	}

	@Override
	public long getConnectionCount() {
		return connectionCount.get();
	}

}
