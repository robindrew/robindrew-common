package com.robindrew.common.text.formattedtable;

import java.util.Collection;
import java.util.List;

public interface IFormattedTable {

	void addRow(Collection<? extends Object> row);

	void addRow(Object... values);

	List<IColumn> getColumns();

	IColumn getColumn(int index);

	int getLength(int index);

	int columns();

	int rows();

	List<List<String>> getRows();

	List<String> getRow(int index);

	public interface IColumn {

		int length();

		String getName();

		int getIndex();

	}
}
