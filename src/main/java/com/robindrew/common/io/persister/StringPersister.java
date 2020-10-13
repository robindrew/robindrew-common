package com.robindrew.common.io.persister;

import static com.google.common.base.Charsets.UTF_8;

import java.io.IOException;
import java.nio.charset.Charset;

import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;

public class StringPersister implements IObjectPersister<String> {

	private Charset charset = UTF_8;

	public void setCharset(Charset charset) {
		if (charset == null) {
			throw new NullPointerException("charset");
		}
		this.charset = charset;
	}

	@Override
	public void writeTo(ByteSink sink, String value) throws IOException {
		sink.asCharSink(charset).write(value);
	}

	@Override
	public String readFrom(ByteSource source) throws IOException {
		return source.asCharSource(charset).read();
	}

}
