package com.robindrew.common.io.persister;

import java.io.IOException;
import java.nio.charset.Charset;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.robindrew.common.text.parser.IStringParser;

public class StringParserPersister<V> implements IObjectPersister<V> {

	private final IStringParser<V> parser;
	private final Charset charset;

	public StringParserPersister(IStringParser<V> parser, Charset charset) {
		if (parser == null) {
			throw new NullPointerException("parser");
		}
		if (charset == null) {
			throw new NullPointerException("charset");
		}
		this.parser = parser;
		this.charset = charset;
	}

	public StringParserPersister(IStringParser<V> parser) {
		this(parser, Charsets.UTF_8);
	}

	public IStringParser<V> getParser() {
		return parser;
	}

	@Override
	public void writeTo(ByteSink sink, V value) throws IOException {
		String text = value.toString();
		sink.asCharSink(charset).write(text);
	}

	@Override
	public V readFrom(ByteSource source) throws IOException {
		String text = source.asCharSource(charset).read();
		return parser.parse(text);
	}

}
