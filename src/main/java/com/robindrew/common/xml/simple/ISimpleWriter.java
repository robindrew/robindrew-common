package com.robindrew.common.xml.simple;

import java.io.File;

public interface ISimpleWriter {

	<T> String writeToString(T instance);

	<T> void writeToFile(T instance, String filename);

	<T> void writeToFile(T instance, File file);

}
