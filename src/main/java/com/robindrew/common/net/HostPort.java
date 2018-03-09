package com.robindrew.common.net;

import com.robindrew.common.util.Check;

public class HostPort implements IHostPort {

	public static final HostPort localhost(int port) {
		return new HostPort("localhost", port);
	}

	private final String host;
	private final int port;

	public HostPort(String host, int port) {
		this.host = Check.notEmpty("host", host);
		this.port = Check.min("port", port, 1);
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public int hashCode() {
		return 1999 * host.hashCode() * port;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object instanceof IHostPort) {
			IHostPort that = (IHostPort) object;
			return this.getPort() == that.getPort() && this.getHost().equals(that.getHost());
		}
		return false;
	}

	@Override
	public String toString() {
		return host + ':' + port;
	}

	@Override
	public int compareTo(IHostPort that) {
		if (!this.getHost().equals(that.getHost())) {
			return this.getHost().compareTo(that.getHost());
		}
		return Integer.compare(this.getPort(), that.getPort());
	}
}
