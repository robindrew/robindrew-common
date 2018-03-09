package com.robindrew.common.codec;

import java.net.URLEncoder;
import java.nio.charset.Charset;

import com.google.common.base.Charsets;
import com.robindrew.common.util.Java;

public class UrlEncoder implements IStringEncoder {

	public static final String encode(CharSequence text) {
		UrlEncoder encoder = new UrlEncoder();
		return encoder.encodeToString(text);
	}

	public static final String encode(CharSequence text, Charset charset) {
		UrlEncoder encoder = new UrlEncoder();
		encoder.setCharset(charset);
		return encoder.encodeToString(text);
	}

	private Charset charset = Charsets.UTF_8;

	@Override
	public String encodeToString(Object object) {
		return encodeToString(object.toString());
	}

	@Override
	public String encodeToString(CharSequence text) {
		try {
			return URLEncoder.encode(text.toString(), charset.name());
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	public void setCharset(Charset charset) {
		if (charset == null) {
			throw new NullPointerException("charset");
		}
		this.charset = charset;
	}

	public Charset getCharset() {
		return charset;
	}

}
