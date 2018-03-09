package com.robindrew.common.io.stream;

import java.awt.image.RenderedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.zip.GZIPOutputStream;

import javax.imageio.ImageIO;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.google.common.base.Charsets;
import com.robindrew.common.util.Java;
import com.robindrew.common.util.Quietly;

public abstract class StreamOutput implements IStreamOutput {

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
	public boolean isBuffered() {
		return bufferSize > 0;
	}

	@Override
	public Charset getCharset() {
		return charset;
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
	public OutputStream newOutputStream() {
		OutputStream output = null;
		try {
			output = asByteSink().openStream();
			if (isBuffered()) {
				output = new BufferedOutputStream(output, getBufferSize());
			}
			return output;
		} catch (Throwable t) {
			Quietly.close(output);
			throw Java.propagate(t);
		}
	}

	@Override
	public Writer newWriter() {
		Writer writer = null;
		try {
			writer = asCharSink().openStream();
			if (isBuffered()) {
				writer = new BufferedWriter(writer, getBufferSize());
			}
			return writer;
		} catch (Throwable t) {
			Quietly.close(writer);
			throw Java.propagate(t);
		}
	}

	@Override
	public BufferedOutputStream newBufferedOutputStream() {
		OutputStream output = null;
		try {
			output = asByteSink().openStream();
			if (isBuffered()) {
				return new BufferedOutputStream(output, getBufferSize());
			} else {
				return new BufferedOutputStream(output);
			}
		} catch (Throwable t) {
			Quietly.close(output);
			throw Java.propagate(t);
		}
	}

	@Override
	public BufferedWriter newBufferedWriter() {
		Writer writer = null;
		try {
			writer = asCharSink().openStream();
			if (isBuffered()) {
				return new BufferedWriter(writer, getBufferSize());
			} else {
				return new BufferedWriter(writer);
			}
		} catch (Throwable t) {
			Quietly.close(writer);
			throw Java.propagate(t);
		}
	}

	@Override
	public GZIPOutputStream newGzipOutputStream() {
		OutputStream stream = newOutputStream();
		try {
			return new GZIPOutputStream(stream);
		} catch (Throwable t) {
			Quietly.close(stream);
			throw Java.propagate(t);
		}
	}

	@Override
	public void writeFromBytes(byte[] bytes) {
		writeFromBytes(bytes, 0, bytes.length);
	}

	@Override
	public void writeFromBytes(byte[] bytes, int offset, int length) {
		try (OutputStream output = newOutputStream()) {
			output.write(bytes, offset, length);
			output.flush();
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public int writeFromStream(InputStream input) {
		int bytesWritten = 0;
		try (OutputStream output = newOutputStream()) {
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
		}
		return bytesWritten;
	}

	@Override
	public void writeFromProperties(Properties properties, boolean isXML) {
		try (OutputStream output = newOutputStream()) {
			if (isXML) {
				properties.storeToXML(output, null);
			} else {
				properties.store(output, null);
			}
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public void writeFromChars(char[] text) {
		writeFromChars(text, 0, text.length);
	}

	@Override
	public void writeFromChars(char[] text, int offset, int length) {
		try (Writer writer = newWriter()) {
			writer.write(text, offset, length);
			writer.flush();
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public void writeFromString(String text) {
		try (Writer writer = newWriter()) {
			writer.write(text);
			writer.flush();
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public int writeFromReader(Reader reader) {
		int charsWritten = 0;
		try (Writer writer = newWriter()) {
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
		}
		return charsWritten;
	}

	@Override
	public void writeFromLines(Iterable<String> collection) {
		String lineSeparator = Java.getLineSeparator();
		try (Writer writer = newWriter()) {
			for (String line : collection) {
				if (line != null) {
					writer.write(line);
					writer.write(lineSeparator);
				}
			}
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public void writeFromImage(RenderedImage image, String format) {
		try (OutputStream output = newOutputStream()) {
			ImageIO.write(image, format, output);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public <T> void writeFromSimpleXmlObject(Class<T> type) {
		try (Writer writer = newWriter()) {
			Serializer serializer = new Persister();
			serializer.write(type, writer);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public <T> void writeFromSimpleXmlObject(T instance) {
		try (Writer writer = newWriter()) {
			Serializer serializer = new Persister();
			serializer.write(instance, writer);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}
}
