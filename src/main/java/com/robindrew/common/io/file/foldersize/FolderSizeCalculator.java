package com.robindrew.common.io.file.foldersize;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.robindrew.common.util.Java;

public class FolderSizeCalculator {

	private final File directory;

	public FolderSizeCalculator(File directory) {
		if (!directory.exists()) {
			throw new IllegalArgumentException("Directory does not exist: " + directory);
		}
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException("File is not a directory: " + directory);
		}
		this.directory = directory;
	}

	public FolderSizeReport newReport() {
		FolderSizeReport report = new FolderSizeReport();
		try {
			Files.walkFileTree(directory.toPath(), report);
		} catch (IOException e) {
			throw Java.propagate(e);
		}
		return report;
	}

}
