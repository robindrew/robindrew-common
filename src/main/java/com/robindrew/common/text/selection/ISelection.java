package com.robindrew.common.text.selection;

public interface ISelection {

	void select(String text);

	boolean select(String begin, String end, SelectionOption... options);

	boolean selectFrom(String begin, SelectionOption... options);

	boolean selectTo(String end, SelectionOption... options);

	String deselect();

	String get();

	String extract(String begin, String end, SelectionOption... options);

	String extractFrom(String begin, SelectionOption... options);

	String extractTo(String end, SelectionOption... options);

	void clear();
}
