package com.robindrew.common.codec;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

import com.google.common.base.Charsets;

public class Base64Decoder implements IStringDecoder {

	private Charset charset = Charsets.UTF_8;

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	@Override
	public Object decodeToObject(String text) {
		return decodeToString(text);
	}

	@Override
	public String decodeToString(CharSequence text) {
		byte[] encoded = text.toString().getBytes(charset);
		byte[] decoded = new Base64().decode(encoded);
		return new String(decoded);
	}

}
