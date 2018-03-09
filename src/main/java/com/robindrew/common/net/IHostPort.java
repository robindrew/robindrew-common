package com.robindrew.common.net;

public interface IHostPort extends Comparable<IHostPort> {

	String getHost();

	int getPort();
}
