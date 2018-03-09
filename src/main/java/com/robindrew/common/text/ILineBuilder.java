package com.robindrew.common.text;

public interface ILineBuilder extends CharSequence {

	void setLineSeparator(String separator);

	ILineBuilder append(Object value);

	ILineBuilder append(String value);

	ILineBuilder append(byte value);

	ILineBuilder append(short value);

	ILineBuilder append(int value);

	ILineBuilder append(long value);

	ILineBuilder append(float value);

	ILineBuilder append(double value);

	ILineBuilder append(boolean value);

	ILineBuilder append(char value);

	ILineBuilder append(char[] value);

	ILineBuilder append(char[] value, int offset, int length);

	ILineBuilder append(CharSequence value);

	ILineBuilder append(CharSequence value, int start, int end);

	ILineBuilder appendLine();

	ILineBuilder appendLine(Object value);

	ILineBuilder appendLine(String value);

	ILineBuilder appendLine(Object... values);

	ILineBuilder appendLine(String... values);

	ILineBuilder separator(String separator);

	ILineBuilder separator(String separator, int fromIndex);

	ILineBuilder clear();
}
