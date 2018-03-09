package com.robindrew.common.xml.xstream;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;

import com.robindrew.common.codec.IObjectEncoder;

public interface IXStream extends IObjectEncoder {

	void alias(Class<?> type);

	void aliasAll(Collection<? extends Class<?>> types);

	void attribute(Class<?> type);

	<T> String toXml(T type);

	<T> String toXml(T type, boolean formatted);

	<T> void toXml(T type, OutputStream output);

	<T> void toXml(T type, Writer writer);

	<T> void toXml(T type, File file);

	<T> T fromXml(String xml);

	<T> T fromXml(InputStream input);

	<T> T fromXml(Reader reader);

	<T> T fromXml(File file);

}
