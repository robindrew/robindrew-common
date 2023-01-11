package com.robindrew.common.io.file.foldersize;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import com.robindrew.common.util.Java;

public class FolderSizeCalculator {

	public static void main(String[] args) {
		File dir = new File("d:");
		FolderSizeReport report = new FolderSizeCalculator(dir).newReport();
		FolderSizeFilters filters = new FolderSizeFilters();
		filters.setMaxFolderCount(10);
		filters.setMaxFolderDepth(1);
		List<FolderSize> list = report.getFolderSizeList(filters);
		for (FolderSize size : list) {
			System.out.println(size);
		}
	}

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
