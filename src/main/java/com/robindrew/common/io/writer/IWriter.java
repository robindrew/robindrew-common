package com.robindrew.common.io.writer;

import java.io.Writer;

public interface IWriter {

	IWriter append(char value);

	IWriter append(char[] value);

	IWriter append(char[] value, int offset, int length);

	IWriter append(CharSequence value);

	IWriter append(CharSequence value, int offset, int length);

	IWriter append(Object value);

	void flush();

	void close();

	Writer asWriter();

}
