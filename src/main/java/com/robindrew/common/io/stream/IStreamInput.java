package com.robindrew.common.io.stream;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;

import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.XMLReader;

import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.LineProcessor;
import com.robindrew.common.io.handler.IInputStreamHandler;
import com.robindrew.common.io.handler.IReaderHandler;
import com.robindrew.common.io.handler.IXMLEventReaderHandler;
import com.robindrew.common.text.parser.IStringParser;

public interface IStreamInput {

	Charset getCharset();

	void setCharset(Charset charset);

	boolean isBuffered();

	int getBufferSize();

	void setBufferSize(int size);

	CharSource asCharSource();

	ByteSource asByteSource();

	InputStream newInputStream();

	BufferedInputStream newBufferedInputStream();

	Reader newReader();

	BufferedReader newBufferedReader();

	GZIPInputStream newGzipInputStream();

	byte[] readToByteArray();

	byte[] toMd5();

	int readToOutputStream(OutputStream output);

	Properties readToProperties(boolean isXML);

	Document readToDocument(DocumentBuilder builder);

	Document readToDocument(DocumentBuilderFactory factory);

	Document readToDocument();

	int readToWriter(Writer writer);

	String readToString();

	char[] readToCharArray();

	<C extends Collection<String>> C readToCollection(C collection);

	<O, C extends Collection<O>> C readToCollection(C collection, IStringParser<O> parser);

	<O> List<O> readToList(IStringParser<O> parser);

	List<String> readToList();

	void readToReader(XMLReader parser);

	void readToReader(ContentHandler content, ErrorHandler error);

	BufferedImage readToBufferedImage();

	<R> R process(LineProcessor<R> processor);

	<R> R handle(IInputStreamHandler<R> handler);

	<R> R handle(IReaderHandler<R> handler);

	<R> R handle(IXMLEventReaderHandler<R> handler, XMLInputFactory factory);

	<R> R handle(IXMLEventReaderHandler<R> handler);

	<T> T readToSimpleXmlObject(Class<T> type);

	<T> T readToSimpleXmlObject(T instance);

}
