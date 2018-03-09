package com.robindrew.common.io.file.objectstore;

import java.util.Collection;
import java.util.List;

public interface IObjectStoreFile<E> {

	List<E> getAll();

	void setAll(Collection<? extends E> elements);

	E parseFromLine(String line);

	String formatToLine(E element);

}
