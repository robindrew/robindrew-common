package com.robindrew.common.io.stream;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.google.common.base.Charsets;
import com.google.common.io.LineProcessor;
import com.robindrew.common.codec.IEncoder;
import com.robindrew.common.codec.Md5Encoder;
import com.robindrew.common.io.handler.IInputStreamHandler;
import com.robindrew.common.io.handler.IReaderHandler;
import com.robindrew.common.io.handler.IXMLEventReaderHandler;
import com.robindrew.common.text.parser.IStringParser;
import com.robindrew.common.util.Java;
import com.robindrew.common.util.Quietly;

public abstract class StreamInput implements IStreamInput {

	private int bufferSize = 8192;
	private Charset charset = Charsets.UTF_8;

	@Override
	public void setCharset(Charset charset) {
		if (charset == null) {
			throw new NullPointerException("charset");
		}
		this.charset = charset;
	}

	@Override
	public Charset getCharset() {
		return charset;
	}

	@Override
	public boolean isBuffered() {
		return bufferSize > 0;
	}

	@Override
	public int getBufferSize() {
		return bufferSize;
	}

	/**
	 * Set buffer size, a value of zero prevents buffering.
	 */
	@Override
	public void setBufferSize(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("size=" + size);
		}
		this.bufferSize = size;
	}

	@Override
	public InputStream newInputStream() {
		InputStream output = null;
		try {
			output = asByteSource().openStream();
			if (isBuffered()) {
				output = new BufferedInputStream(output, getBufferSize());
			}
			return output;
		} catch (Throwable t) {
			Quietly.close(output);
			throw Java.propagate(t);
		}
	}

	@Override
	public Reader newReader() {
		Reader writer = null;
		try {
			writer = asCharSource().openStream();
			if (isBuffered()) {
				writer = new BufferedReader(writer, getBufferSize());
			}
			return writer;
		} catch (Throwable t) {
			Quietly.close(writer);
			throw Java.propagate(t);
		}
	}

	@Override
	public BufferedInputStream newBufferedInputStream() {
		InputStream output = null;
		try {
			output = asByteSource().openStream();
			if (isBuffered()) {
				return new BufferedInputStream(output, getBufferSize());
			} else {
				return new BufferedInputStream(output);
			}
		} catch (Throwable t) {
			Quietly.close(output);
			throw Java.propagate(t);
		}
	}

	@Override
	public BufferedReader newBufferedReader() {
		Reader writer = null;
		try {
			writer = asCharSource().openStream();
			if (isBuffered()) {
				return new BufferedReader(writer, getBufferSize());
			} else {
				return new BufferedReader(writer);
			}
		} catch (Throwable t) {
			Quietly.close(writer);
			throw Java.propagate(t);
		}
	}

	@Override
	public GZIPInputStream newGzipInputStream() {
		InputStream stream = newInputStream();
		try {
			return new GZIPInputStream(stream);
		} catch (Throwable t) {
			Quietly.close(stream);
			throw Java.propagate(t);
		}
	}

	@Override
	public byte[] toMd5() {
		IEncoder encoder = new Md5Encoder();
		return encoder.encodeToBytes(newInputStream());
	}

	@Override
	public byte[] readToByteArray() {
		try {
			InputStream input = newInputStream();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			while (true) {
				int read = input.read();
				if (read == -1) {
					break;
				}
				output.write(read);
			}
			return output.toByteArray();
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public Properties readToProperties(boolean isXML) {
		Properties properties = new Properties();
		InputStream input = newInputStream();
		try {
			if (isXML) {
				properties.loadFromXML(input);
			} else {
				properties.load(input);
			}
			return properties;
		} catch (IOException e) {
			throw Java.propagate(e);
		} finally {
			Quietly.close(input);
		}
	}

	@Override
	public Document readToDocument(DocumentBuilder builder) {
		InputStream input = newInputStream();
		try {
			InputSource source = new InputSource(input);
			return builder.parse(source);
		} catch (Exception e) {
			throw Java.propagate(e);
		} finally {
			Quietly.close(input);
		}
	}

	@Override
	public Document readToDocument(DocumentBuilderFactory factory) {
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw Java.propagate(e);
		}
		return readToDocument(builder);
	}

	@Override
	public Document readToDocument() {
		return readToDocument(DocumentBuilderFactory.newInstance());
	}

	@Override
	public void readToReader(XMLReader parser) {
		InputStream input = newInputStream();
		try {
			parser.parse(new InputSource(input));
		} catch (Exception e) {
			throw Java.propagate(e);
		} finally {
			Quietly.close(input);
		}
	}

	@Override
	public void readToReader(ContentHandler content, ErrorHandler error) {
		XMLReader reader;
		try {
			reader = XMLReaderFactory.createXMLReader();
		} catch (SAXException e) {
			throw Java.propagate(e);
		}
		reader.setContentHandler(content);
		reader.setErrorHandler(error);
		readToReader(reader);
	}

	@Override
	public <C extends Collection<String>> C readToCollection(C collection) {
		BufferedReader reader = newBufferedReader();
		try {
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				collection.add(line);
			}
		} catch (Exception e) {
			throw Java.propagate(e);
		} finally {
			Quietly.close(reader);
		}
		return collection;
	}

	@Override
	public <O, C extends Collection<O>> C readToCollection(C collection, IStringParser<O> parser) {
		BufferedReader reader = (BufferedReader) newReader();
		try {
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				O object = parser.parse(line);
				collection.add(object);
			}
		} catch (Exception e) {
			throw Java.propagate(e);
		} finally {
			Quietly.close(reader);
		}
		return collection;
	}

	@Override
	public <O> List<O> readToList(IStringParser<O> parser) {
		return readToCollection(new ArrayList<O>(), parser);
	}

	@Override
	public List<String> readToList() {
		return readToCollection(new ArrayList<String>());
	}

	@Override
	public String readToString() {
		StringWriter writer = new StringWriter();
		readToWriter(writer);
		return writer.toString();
	}

	@Override
	public char[] readToCharArray() {
		CharArrayWriter writer = new CharArrayWriter();
		readToWriter(writer);
		return writer.toCharArray();
	}

	@Override
	public int readToWriter(Writer writer) {
		int charsWritten = 0;
		final Reader reader = newReader();
		try {
			char[] buffer = new char[getBufferSize()];
			while (true) {
				int charsRead = reader.read(buffer);
				if (charsRead < 1) {
					break;
				}
				writer.write(buffer, 0, charsRead);
				charsWritten += charsRead;
			}
		} catch (IOException e) {
			throw Java.propagate(e);
		} finally {
			Quietly.close(reader);
		}
		return charsWritten;
	}

	@Override
	public int readToOutputStream(OutputStream output) {
		int bytesWritten = 0;
		final InputStream input = newInputStream();
		try {
			byte[] buffer = new byte[getBufferSize()];
			while (true) {
				int bytesRead = input.read(buffer);
				if (bytesRead < 1) {
					break;
				}
				output.write(buffer, 0, bytesRead);
				bytesWritten += bytesRead;
			}
		} catch (IOException e) {
			throw Java.propagate(e);
		} finally {
			Quietly.close(input);
		}
		return bytesWritten;
	}

	@Override
	public <R> R handle(IInputStreamHandler<R> handler) {
		InputStream input = newInputStream();
		try {
			return handler.handle(input);
		} catch (Exception e) {
			throw Java.propagate(e);
		} finally {
			Quietly.close(input);
		}
	}

	@Override
	public <R> R handle(IReaderHandler<R> handler) {
		Reader reader = newReader();
		try {
			return handler.handle(reader);
		} catch (Exception e) {
			throw Java.propagate(e);
		} finally {
			Quietly.close(reader);
		}
	}

	@Override
	public <R> R handle(IXMLEventReaderHandler<R> handler, XMLInputFactory factory) {
		try (InputStream input = newInputStream()) {
			XMLEventReader reader = factory.createXMLEventReader(input);
			return handler.handle(reader);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public <R> R handle(IXMLEventReaderHandler<R> handler) {
		return handle(handler, XMLInputFactory.newFactory());
	}

	@Override
	public BufferedImage readToBufferedImage() {
		try (InputStream input = newInputStream()) {
			return ImageIO.read(input);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public <R> R process(LineProcessor<R> processor) {
		try (BufferedReader reader = newBufferedReader()) {
			String line;
			while ((line = reader.readLine()) != null) {
				processor.processLine(line);
			}
			return processor.getResult();
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public <T> T readToSimpleXmlObject(Class<T> type) {
		try (Reader reader = newReader()) {
			Serializer serializer = new Persister();
			return serializer.read(type, reader);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public <T> T readToSimpleXmlObject(T instance) {
		try (Reader reader = newReader()) {
			Serializer serializer = new Persister();
			return serializer.read(instance, reader);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}
}
