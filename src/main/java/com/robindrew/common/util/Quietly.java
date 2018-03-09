package com.robindrew.common.util;

import java.io.Closeable;
import java.io.Flushable;
import java.net.Socket;
import java.nio.channels.Selector;
import java.util.concurrent.ExecutorService;

import javax.imageio.stream.ImageOutputStreamImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Quietly {

	private static final Logger log = LoggerFactory.getLogger(Quietly.class);

	public static final Exception close(Socket socket) {
		if (socket == null) {
			return null;
		}
		try {
			if (!socket.isClosed()) {
				socket.close();
			}
			return null;
		} catch (Exception e) {
			log.warn("Error closing socket: " + socket, e);
			return e;
		}
	}

	public static final Exception close(Selector selector) {
		if (selector == null) {
			return null;
		}
		try {
			if (selector.isOpen()) {
				selector.close();
			}
			return null;
		} catch (Exception e) {
			log.warn("Error closing selector: " + selector, e);
			return e;
		}
	}

	public static final Exception close(Closeable closeable) {
		if (closeable == null) {
			return null;
		}
		try {
			closeable.close();
			return null;
		} catch (Exception e) {
			log.warn("Error closing: " + closeable, e);
			return e;
		}
	}

	public static final Exception close(AutoCloseable closeable) {
		if (closeable == null) {
			return null;
		}
		try {
			closeable.close();
			return null;
		} catch (Exception e) {
			log.warn("Error closing: " + closeable, e);
			return e;
		}
	}

	public static Exception close(ImageOutputStreamImpl closeable) {
		if (closeable == null) {
			return null;
		}
		try {
			closeable.close();
			return null;
		} catch (Exception e) {
			log.warn("Error closing: " + closeable, e);
			return e;
		}
	}

	public static Exception shutdown(ExecutorService executor) {
		if (executor == null) {
			return null;
		}
		try {
			executor.shutdown();
			return null;
		} catch (Exception e) {
			log.warn("Error shutting down: " + executor, e);
			return e;
		}
	}

	public static Exception flush(Flushable flushable) {
		if (flushable == null) {
			return null;
		}
		try {
			flushable.flush();
			return null;
		} catch (Exception e) {
			log.warn("Error flushing: " + flushable, e);
			return e;
		}
	}

	private Quietly() {
	}

}
