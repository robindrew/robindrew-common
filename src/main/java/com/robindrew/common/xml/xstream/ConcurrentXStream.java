package com.robindrew.common.xml.xstream;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;

public class ConcurrentXStream extends AliasXStream {

	@Override
	public void alias(Class<?> type) {
		synchronized (this) {
			super.alias(type);
		}
	}

	@Override
	public void aliasAll(Collection<? extends Class<?>> types) {
		synchronized (this) {
			super.aliasAll(types);
		}
	}

	@Override
	public void attribute(Class<?> type) {
		synchronized (this) {
			super.attribute(type);
		}
	}

	@Override
	public <T> String toXml(T type) {
		synchronized (this) {
			return super.toXml(type);
		}
	}

	@Override
	public <T> void toXml(T type, OutputStream output) {
		synchronized (this) {
			super.toXml(type, output);
		}
	}

	@Override
	public <T> void toXml(T type, Writer writer) {
		synchronized (this) {
			super.toXml(type, writer);
		}
	}

	@Override
	public <T> T fromXml(String xml) {
		synchronized (this) {
			return super.fromXml(xml);
		}
	}

	@Override
	public <T> T fromXml(InputStream input) {
		synchronized (this) {
			return super.fromXml(input);
		}
	}

	@Override
	public <T> T fromXml(Reader reader) {
		synchronized (this) {
			return super.fromXml(reader);
		}
	}

}
