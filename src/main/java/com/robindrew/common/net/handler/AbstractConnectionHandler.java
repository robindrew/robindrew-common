package com.robindrew.common.net.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.common.base.Throwables;
import com.robindrew.common.net.connection.IConnection;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Quietly;

public abstract class AbstractConnectionHandler implements IConnectionHandler {

	private static final Logger log = LoggerFactory.getLogger(AbstractConnectionHandler.class);

	private final IConnection connection;
	private final long number;

	protected AbstractConnectionHandler(IConnection connection, long number) {
		this.connection = Check.notNull("connection", connection);
		this.number = Check.min("number", number, 1);
	}

	@Override
	public long getNumber() {
		return number;
	}

	@Override
	public IConnection getConnection() {
		return connection;
	}

	@Override
	public String toString() {
		return "#" + number + "/" + connection;
	}

	@Override
	public void run() {
		try {
			handle();
		} finally {
			handled();
		}
	}

	public void handle() {
		Stopwatch timer = Stopwatch.createStarted();
		timer.start();
		try {

			// Handle!
			handleConnection();

			// Flush (necessary)
			getConnection().getOutput().flush();

		} catch (Throwable t) {
			handleError(t);

		} finally {
			timer.stop();
			log.info("Connection Completed: " + this + " in " + timer);
		}
	}

	protected void handleError(Throwable t) {
		t = Throwables.getRootCause(t);
		log.error("Connection Error: " + this, t);
	}

	protected void handled() {
		Quietly.close(connection);
	}

	protected abstract void handleConnection() throws IOException;
}
