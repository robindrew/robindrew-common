package com.robindrew.common.net.connection;

import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.date.UnitTime;
import com.robindrew.common.util.Threads;

public class SocketConnector implements ISocketConnector {

	private static final Logger log = LoggerFactory.getLogger(SocketConnector.class);

	private static final int INFINITE = 0;

	private Optional<UnitTime> retryFrequency = Optional.empty();
	private int retryCount = INFINITE;

	public void setRetryFrequency(UnitTime frequency) {
		this.retryFrequency = Optional.of(frequency);
	}

	public void setRetryCount(int count) {
		if (count < INFINITE) {
			throw new IllegalArgumentException("count=" + count);
		}
		this.retryCount = count;
	}

	@Override
	public Socket connect(SocketAddress address, int connectTimeoutInMillis) {
		try {
			int attempt = 0;
			while (true) {

				// Attempt connect ...
				try {
					if (attempt > 0) {
						log.warn("Retry to connecting to " + address);
					}
					Socket socket = new Socket();
					socket.connect(address, connectTimeoutInMillis);
					return socket;
				}

				// Connect failed, retry?
				catch (ConnectException ce) {

					// Not infinite retries? check the attempt ...
					if (retryCount > INFINITE) {
						if (retryCount <= attempt) {
							throw ce;
						}
					}

					// Retry frequency not set?
					if (!retryFrequency.isPresent()) {
						throw ce;
					}

					log.warn("Failed to connect to " + address + ", cause: " + ce.getMessage() + " (retry in " + retryFrequency.get() + ")");
					Threads.sleep(retryFrequency.get());
					attempt++;
				}
			}
		} catch (Exception e) {
			throw new SocketConnectException("Failed to connect to " + address + " in " + connectTimeoutInMillis + " ms", e);
		}
	}

}
