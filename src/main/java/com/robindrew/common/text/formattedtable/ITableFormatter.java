package com.robindrew.common.text.formattedtable;

public interface ITableFormatter {

	String formatTable(IFormattedTable table);

	void formatTable(IFormattedTable table, StringBuilder formatted);

}
