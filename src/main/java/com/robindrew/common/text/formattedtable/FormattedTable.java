package com.robindrew.common.text.formattedtable;

import static java.util.Collections.unmodifiableList;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Table;

public class FormattedTable implements IFormattedTable {

	public static <R, C, V> IFormattedTable copyOf(Table<R, C, V> table, int maxCellSize) {
		Set<C> columns = table.columnKeySet();
		if (columns.isEmpty()) {
			throw new IllegalArgumentException("No columns in table");
		}

		// Extract the columns
		IFormattedTable formatted = new FormattedTable(maxCellSize, columns);

		// Rows
		Map<R, Map<C, V>> rows = table.rowMap();
		for (Map<C, V> row : rows.values()) {
			List<V> values = new ArrayList<>(columns.size());
			for (C column : columns) {
				V value = row.get(column);
				values.add(value);
			}
			formatted.addRow(values);
		}

		return formatted;
	}

	/** The maximum size of any cell. */
	private final int maxCellSize;
	/** The columns. */
	private final List<Column> columns;
	/** The rows. */
	private final List<List<String>> rows = new ArrayList<>();

	public FormattedTable(int maxCellSize, Object... columnNames) {
		this(maxCellSize, Arrays.asList(columnNames));
	}

	public FormattedTable(int maxCellSize, Collection<? extends Object> columnNames) {
		if (maxCellSize < 1) {
			throw new IllegalArgumentException("maxCellSize=" + maxCellSize);
		}
		if (columnNames.isEmpty()) {
			throw new IllegalArgumentException("columnNames is empty");
		}
		this.maxCellSize = maxCellSize;
		this.columns = toColumns(columnNames);
	}

	private List<Column> toColumns(Collection<? extends Object> names) {
		List<Column> list = new ArrayList<>(names.size());
		int index = 0;
		for (Object name : names) {
			String text = format(name);
			list.add(new Column(text, index++));
		}
		return list;
	}

	private String format(Object object) {
		if (object == null) {
			return format("null");
		}
		String text = object.toString();

		int length = text.length();
		if (length > maxCellSize) {
			length = maxCellSize;
		}

		StringBuilder formatted = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			char c = text.charAt(i);

			String replace = formatChar(c);
			if (replace != null) {
				if (!replace.isEmpty()) {
					formatted.append(replace);
				}
			} else {
				formatted.append(c);
			}
		}

		return formatted.toString();
	}

	private String formatChar(char c) {
		// Returning null will append the character as is
		// Returning an empty string will skip appending anything
		switch (c) {
			case '\n':
				return "\\n";
			case '\r':
				return "\\r";
			case '\t':
				return "\\t";
			default:
				return null;
		}
	}

	@Override
	public void addRow(Collection<? extends Object> row) {
		rows.add(toRow(row));
	}

	private List<String> toRow(Collection<? extends Object> row) {
		if (row.size() != columns.size()) {
			throw new IllegalArgumentException("Row must have correct number of columns");
		}

		List<String> list = new ArrayList<>(row.size());

		int index = 0;
		for (Object cell : row) {
			String text = format(cell);
			list.add(text);

			Column column = getColumn(index++);
			if (column.length() < text.length()) {
				column.setLength(text.length());
			}
		}
		return list;
	}

	@Override
	public List<IColumn> getColumns() {
		return unmodifiableList(columns);
	}

	@Override
	public int getLength(int index) {
		return getColumn(index).length();
	}

	@Override
	public int columns() {
		return columns.size();
	}

	@Override
	public int rows() {
		return rows.size();
	}

	@Override
	public List<List<String>> getRows() {
		return unmodifiableList(rows);
	}

	@Override
	public List<String> getRow(int index) {
		return unmodifiableList(rows.get(index));
	}

	@Override
	public Column getColumn(int index) {
		return columns.get(index);
	}

	public static class Column implements IColumn {

		private final String name;
		private final int index;
		private int length;

		public Column(String name, int index) {
			this.name = name;
			this.index = index;
			this.length = name.length();
		}

		public void setLength(int length) {
			this.length = length;
		}

		@Override
		public int length() {
			return length;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getIndex() {
			return index;
		}

		@Override
		public String toString() {
			return reflectionToString(this, SHORT_PREFIX_STYLE);
		}
	}

	@Override
	public void addRow(Object... values) {
		addRow(Arrays.asList(values));
	}

}
