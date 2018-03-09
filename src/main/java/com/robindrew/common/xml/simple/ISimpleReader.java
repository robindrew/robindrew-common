package com.robindrew.common.xml.simple;

import java.io.File;

public interface ISimpleReader {

	<T> T readString(Class<T> type, String xml);

	<T> T readString(T instance, String xml);

	<T> T readFile(Class<T> type, File file);

	<T> T readFile(Class<T> type, String filename);

	<T> T readFile(T instance, File file);

	<T> T readFile(T instance, String filename);

	<T> T readResource(Class<T> type, String name);

	<T> T readResource(T instance, String name);

}
