package com.robindrew.common.io.data.server;

import java.io.IOException;
import java.net.Socket;

import com.robindrew.common.io.data.DataReader;
import com.robindrew.common.io.data.DataWriter;
import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.util.Quietly;

public class DataConnection implements IDataConnection {

	private final Socket socket;
	private final IDataReader reader;
	private final IDataWriter writer;

	public DataConnection(Socket socket) throws IOException {
		this.socket = socket;
		this.reader = new DataReader(socket.getInputStream());
		this.writer = new DataWriter(socket.getOutputStream());
	}

	public DataConnection(String host, int port) throws IOException {
		this(new Socket(host, port));
	}

	public Socket getSocket() {
		return socket;
	}

	@Override
	public boolean isClosed() {
		return socket.isClosed();
	}

	@Override
	public IDataReader getReader() {
		return reader;
	}

	@Override
	public IDataWriter getWriter() {
		return writer;
	}

	@Override
	public void close() {
		Quietly.close(socket);
	}

}
