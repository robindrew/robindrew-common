package com.robindrew.common.http.header;

public enum HttpHeader implements IHttpHeader {

	/** Host. */
	HOST("Host"),
	/** User-Agent. */
	USER_AGENT("User-Agent"),
	/** Location. */
	LOCATION("Location"),
	/** Pragma. */
	PRAGMA("Pragma"),
	/** Expires. */
	EXIPRES("Expires"),
	/** Cache-Control. */
	CACHE_CONTROL("Cache-Control"),
	/** Content-Type. */
	CONTENT_TYPE("Content-Type"),
	/** Content-Length. */
	CONTENT_LENGTH("Content-Length"),
	/** Content-Encoding. */
	CONTENT_ENCODING("Content-Encoding"),
	/** Cookie. */
	COOKIE("Cookie"),
	/** Accept. */
	ACCEPT("Accept"),
	/** Set Cookie. */
	SET_COOKIE("Set-Cookie"),
	/** Authentication. */
	WWW_AUTHENTICATE("WWW-Authenticate"),
	/** Authorization. */
	AUTHORIZATION("Authorization");

	private final String text;
	private final String lower;

	private HttpHeader(String text) {
		this.text = text;
		this.lower = text.toLowerCase();
	}

	@Override
	public String get() {
		return text;
	}

	@Override
	public String getLower() {
		return lower;
	}

	public static HttpHeader parseHeader(String text) {
		String lower = text.toLowerCase();
		for (HttpHeader header : values()) {
			if (header.getLower().equals(lower)) {
				return header;
			}
		}
		throw new IllegalArgumentException("text: '" + text + "'");
	}

	public boolean isNamed(String named) {
		return text.equalsIgnoreCase(named);
	}

}
