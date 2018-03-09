package com.robindrew.common.codec;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

import com.google.common.base.Charsets;

public class Base64Encoder implements IStringEncoder {

	private Charset charset = Charsets.UTF_8;

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	@Override
	public String encodeToString(Object object) {
		return encodeToString(object.toString());
	}

	@Override
	public String encodeToString(CharSequence text) {
		byte[] decoded = text.toString().getBytes(charset);
		byte[] encoded = new Base64().encode(decoded);
		return new String(encoded);
	}

}
