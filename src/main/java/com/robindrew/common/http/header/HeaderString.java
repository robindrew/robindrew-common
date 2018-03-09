package com.robindrew.common.http.header;

import com.robindrew.common.util.Check;

public class HeaderString implements IHttpHeader {

	private final String header;

	public HeaderString(String header) {
		this.header = Check.notEmpty("header", header);
	}

	@Override
	public String get() {
		return header;
	}

	@Override
	public String getLower() {
		return header.toLowerCase();
	}
}
