package com.robindrew.common.io.stream;

import java.awt.image.RenderedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.zip.GZIPOutputStream;

import com.google.common.io.ByteSink;
import com.google.common.io.CharSink;

public interface IStreamOutput {

	Charset getCharset();

	void setCharset(Charset charset);

	boolean isBuffered();

	int getBufferSize();

	void setBufferSize(int size);

	CharSink asCharSink();

	ByteSink asByteSink();

	OutputStream newOutputStream();

	BufferedOutputStream newBufferedOutputStream();

	Writer newWriter();

	BufferedWriter newBufferedWriter();

	GZIPOutputStream newGzipOutputStream();

	int writeFromStream(InputStream input);

	void writeFromBytes(byte[] content);

	void writeFromBytes(byte[] content, int offset, int length);

	void writeFromChars(char[] text);

	void writeFromChars(char[] text, int offset, int length);

	void writeFromString(String text);

	void writeFromProperties(Properties properties, boolean isXML);

	int writeFromReader(Reader reader);

	void writeFromLines(Iterable<String> collection);

	void writeFromImage(RenderedImage image, String format);

	<T> void writeFromSimpleXmlObject(Class<T> type);

	<T> void writeFromSimpleXmlObject(T instance);

}
