package com.robindrew.common.text.parser;

import com.robindrew.common.net.HostPort;
import com.robindrew.common.net.IHostPort;

public class HostPortParser extends ObjectParser<IHostPort> {

	@Override
	protected IHostPort parseObject(String value) {
		int colon = value.indexOf(':');
		if (colon == -1) {
			throw new IllegalArgumentException("colon missing from host and port: '" + value + "'");
		}
		String host = value.substring(0, colon);
		int port = Integer.parseInt(value.substring(colon + 1));
		return new HostPort(host, port);
	}
}
