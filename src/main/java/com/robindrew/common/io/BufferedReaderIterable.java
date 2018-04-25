package com.robindrew.common.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Iterator;

import com.google.common.base.Charsets;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Java;

public class BufferedReaderIterable implements AutoCloseable, Iterable<String> {

	public static BufferedReaderIterable lines(Reader reader) {
		if (reader instanceof BufferedReader) {
			return new BufferedReaderIterable((BufferedReader) reader);
		}
		return new BufferedReaderIterable(new BufferedReader(reader));
	}

	public static BufferedReaderIterable lines(InputStream input, Charset charset) {
		return lines(new InputStreamReader(input, charset));
	}

	public static BufferedReaderIterable lines(InputStream input) {
		return lines(input, Charsets.UTF_8);
	}

	private final BufferedReader reader;

	public BufferedReaderIterable(BufferedReader reader) {
		this.reader = Check.notNull("reader", reader);
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}

	@Override
	public Iterator<String> iterator() {
		return new BufferedReaderIterator();
	}

	private class BufferedReaderIterator implements Iterator<String> {

		private String next = null;
		private boolean endOfStream = false;

		private void nextLine() {
			try {
				next = reader.readLine();
			} catch (IOException e) {
				throw Java.propagate(e);
			}
			if (next == null) {
				endOfStream = true;
			}
		}

		@Override
		public boolean hasNext() {
			if (!endOfStream) {
				nextLine();
			}
			return !endOfStream;
		}

		@Override
		public String next() {
			if (endOfStream) {
				throw new IllegalStateException("end of stream");
			}
			nextLine();
			return next;
		}

	}
}
