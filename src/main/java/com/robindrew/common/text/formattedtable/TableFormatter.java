package com.robindrew.common.text.formattedtable;

import java.util.List;

import com.robindrew.common.text.formattedtable.IFormattedTable.IColumn;
import com.robindrew.common.util.Java;

public class TableFormatter implements ITableFormatter {

	/** The line separator. */
	private String lineSeparator = Java.getLineSeparator();
	/** The number of cell padding spaces. */
	private int cellPadding = 1;
	/** The junction separator. */
	private String junctionSeparator = "+";
	/** The horizontal separator. */
	private String horizontalSeparator = "-";
	/** The vertical separator. */
	private String verticalSeparator = "|";
	/** Indicates whether to display the line separator. */
	private boolean showSeparatorLine = true;
	/** Indicates whether to display the line endings. */
	private boolean showLineEndings = true;
	/** Indicates whether to display the column names. */
	private boolean showColumnNames = true;

	public TableFormatter clearSeparators() {
		cellPadding = 0;
		junctionSeparator = "";
		horizontalSeparator = "";
		verticalSeparator = "  ";
		showSeparatorLine = false;
		showLineEndings = false;
		showColumnNames = true;
		return this;
	}

	@Override
	public String formatTable(IFormattedTable table) {
		StringBuilder formatted = new StringBuilder();
		formatTable(table, formatted);
		return formatted.toString();
	}

	@Override
	public void formatTable(IFormattedTable table, StringBuilder text) {
		if (showColumnNames) {
			appendSeparatorLine(text, table);
			appendColumns(text, table);
		}
		appendSeparatorLine(text, table);
		appendRows(text, table);
		appendSeparatorLine(text, table);
	}

	private void appendRows(StringBuilder text, IFormattedTable table) {
		int finalColumn = table.columns() - 1;
		for (List<String> row : table.getRows()) {
			lineEnding(text, false);
			int columnIndex = 0;
			for (String value : row) {
				int length = table.getLength(columnIndex);
				formatCell(text, value, length, columnIndex++, finalColumn);
			}
			lineEnding(text, true);
		}
	}

	private void appendColumns(StringBuilder text, IFormattedTable table) {
		int finalColumn = table.columns() - 1;
		lineEnding(text, false);
		for (IColumn column : table.getColumns()) {
			formatCell(text, column.getName(), column.length(), column.getIndex(), finalColumn);
		}
		lineEnding(text, true);
	}

	private void formatCell(StringBuilder text, String value, int length, int columnIndex, int finalColumnIndex) {
		if (columnIndex > 0) {
			text.append(verticalSeparator);
		}
		padding(text, " ");
		text.append(value);

		// Special case to allow for appending whitespace unnecessarily to
		// the end of a line that has no line endings
		if (showLineEndings || columnIndex < finalColumnIndex) {
			while (length > value.length()) {
				length--;
				text.append(' ');
			}
			padding(text, " ");
		}
	}

	private void lineEnding(StringBuilder text, boolean newLine) {
		if (showLineEndings) {
			text.append(verticalSeparator);
		}
		if (newLine) {
			text.append(lineSeparator);
		}
	}

	private void appendSeparatorLine(StringBuilder text, IFormattedTable table) {
		if (showSeparatorLine) {
			text.append(junctionSeparator);
			for (IColumn column : table.getColumns()) {
				padding(text, horizontalSeparator);
				for (int i = 0; i < column.length(); i++) {
					text.append(horizontalSeparator);
				}
				padding(text, horizontalSeparator);
				text.append(junctionSeparator);
			}
			text.append(lineSeparator);
		}
	}

	private void padding(StringBuilder text, String padding) {
		for (int i = 0; i < cellPadding; i++) {
			text.append(padding);
		}
	}
}
