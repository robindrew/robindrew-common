package com.robindrew.common.text.selection;

import static com.robindrew.common.text.selection.SelectionOption.IGNORE_CASE;
import static com.robindrew.common.text.selection.SelectionOption.INCLUDE_DELIMITERS;
import static com.robindrew.common.text.selection.SelectionOption.NO_MOVE;
import static com.robindrew.common.text.selection.SelectionOption.OPTIONAL;

import java.util.LinkedList;
import java.util.List;

import com.robindrew.common.text.Strings;

/**
 * A selector for selection and extraction of text.
 */
public class Selection implements ISelection {

	/** The list of selected buffers. */
	private final List<String> bufferList = new LinkedList<String>();

	/**
	 * Creates a new selection.
	 */
	public Selection() {
	}

	/**
	 * Creates a new selection.
	 * @param text the text.
	 */
	public Selection(String text) {
		select(text);
	}

	/**
	 * Selects the given text.
	 * @param text the text to select.
	 */
	@Override
	public final void select(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		bufferList.add(0, text);
	}

	/**
	 * Deselects the currently selected text.
	 * @return the deselected text.
	 */
	@Override
	public final String deselect() {
		return bufferList.remove(0);
	}

	/**
	 * Returns the currently selected text.
	 * @return the text.
	 */
	@Override
	public final String get() {
		return bufferList.get(0);
	}

	private static final boolean isSet(SelectionOption option, SelectionOption[] options) {
		if (options == null || options.length == 0) {
			return false;
		}
		for (SelectionOption element : options) {
			if (element.equals(option)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Extracts and returns the first string beginning and ending with the given delimiters.
	 * @param begin the beginning text.
	 * @param end the end text.
	 * @param options the options.
	 * @return the extracted text.
	 */
	@Override
	public final String extract(String begin, String end, SelectionOption... options) throws SelectionException {
		if (bufferList.size() == 0) {
			if (isSet(OPTIONAL, options)) {
				return null;
			}
			throw new SelectionException("nothing selected to extract from");
		}
		String selection = get();
		int beginIndex = 0;
		int beginLength = 0;
		if (begin != null) {
			if (isSet(IGNORE_CASE, options)) {
				beginIndex = Strings.indexOfIgnoreCase(selection, begin, 0);
			} else {
				beginIndex = selection.indexOf(begin);
			}
			if (beginIndex == -1) {
				if (isSet(OPTIONAL, options)) {
					return null;
				}
				throw new SelectionException("begin text not found: \"" + begin + "\"");
			}
			beginLength = begin.length();
		}
		int endIndex = selection.length();
		int endLength = 0;
		if (end != null) {
			if (isSet(IGNORE_CASE, options)) {
				endIndex = Strings.indexOfIgnoreCase(selection, end, beginIndex + beginLength);
			} else {
				endIndex = selection.indexOf(end, beginIndex + beginLength);
			}
			endLength = end.length();
			if (endIndex == -1) {
				if (isSet(OPTIONAL, options)) {
					return null;
				}
				throw new SelectionException("end text not found: \"" + end + "\"");
			}
		}
		// ESCA-JAVA0177: variables are final
		final String extraction;
		if (isSet(INCLUDE_DELIMITERS, options)) {
			extraction = selection.substring(beginIndex, endIndex + endLength);
		} else {
			extraction = selection.substring(beginIndex + beginLength, endIndex);
		}
		final String newSelection;
		if (isSet(NO_MOVE, options)) {
			newSelection = selection;
		} else {
			newSelection = selection.substring(endIndex + endLength, selection.length());
		}
		deselect();
		select(newSelection);
		return extraction;
	}

	/**
	 * Selects the given text beginning and ending with the given delimiters.
	 * @param begin the beginning text.
	 * @param end the end text.
	 * @param options the options.
	 * @return true if selection successful.
	 */
	@Override
	public final boolean select(String begin, String end, SelectionOption... options) throws SelectionException {
		String extraction = extract(begin, end, options);
		if (extraction == null) {
			return false;
		}
		select(extraction);
		return true;
	}

	/**
	 * Clear the selection.
	 */
	@Override
	public void clear() {
		bufferList.clear();
	}

	@Override
	public boolean selectFrom(String begin, SelectionOption... options) {
		return select(begin, null, options);
	}

	@Override
	public boolean selectTo(String end, SelectionOption... options) {
		return select(null, end, options);
	}

	@Override
	public String extractFrom(String begin, SelectionOption... options) {
		return extract(begin, null, options);
	}

	@Override
	public String extractTo(String end, SelectionOption... options) {
		return extract(null, end, options);
	}

	@Override
	public String toString() {
		return extract(null, null, NO_MOVE);
	}

}