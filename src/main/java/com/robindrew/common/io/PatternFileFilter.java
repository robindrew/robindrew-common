package com.robindrew.common.io;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

public class PatternFileFilter implements FileFilter {

	private final Pattern pattern;

	public PatternFileFilter(String regex) {
		this.pattern = Pattern.compile(regex);
	}

	@Override
	public boolean accept(File file) {
		return pattern.matcher(file.getName()).matches();
	}

}
