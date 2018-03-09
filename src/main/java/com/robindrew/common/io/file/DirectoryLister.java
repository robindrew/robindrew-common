package com.robindrew.common.io.file;

import static java.nio.file.Files.walkFileTree;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.robindrew.common.util.Java;

public class DirectoryLister {

	private boolean checkDirectory = false;
	private boolean recursive = false;
	private boolean notEmpty = false;
	private boolean sort = false;
	private FileFilter filter = null;
	private Comparator<File> comparator = null;

	public DirectoryLister sort(Comparator<File> comparator) {
		this.comparator = comparator;
		this.sort = comparator != null;
		return this;
	}

	public DirectoryLister notEmpty() {
		notEmpty = true;
		return this;
	}

	public DirectoryLister sort() {
		sort = true;
		return this;
	}

	public DirectoryLister filter(FileFilter filter) {
		this.filter = filter;
		return this;
	}

	public DirectoryLister checkDirectory() {
		checkDirectory = true;
		return this;
	}

	public DirectoryLister recursive() {
		recursive = true;
		return this;
	}

	public List<File> listFiles(String directory) {
		return listFiles(new File(directory));
	}

	public List<File> listFiles(File directory) {
		if (!directory.exists()) {
			throw new IllegalArgumentException("File does not exist: " + directory);
		}
		if (checkDirectory && !directory.isDirectory()) {
			throw new IllegalArgumentException("File is not a directory: " + directory);
		}
		List<File> fileList = listFiles(directory, recursive);
		if (notEmpty && fileList.isEmpty()) {
			throw new IllegalArgumentException("Directory is empty: " + directory);
		}
		if (sort) {
			if (comparator != null) {
				Collections.sort(fileList, comparator);
			} else {
				Collections.sort(fileList);
			}
		}
		return fileList;

	}

	private List<File> listFiles(File directory, boolean recursive) {
		List<File> fileList = new ArrayList<>();

		// Recursive file tree list
		if (recursive) {
			try {
				walkFileTree(directory.toPath(), new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
						File file = path.toFile();
						if (filter == null || filter.accept(file)) {
							fileList.add(file);
						}
						return FileVisitResult.CONTINUE;
					}

				});
			} catch (IOException e) {
				throw Java.propagate(e);
			}
		}

		// Non-recursive simple file list
		else {
			File[] files = directory.listFiles();
			if (files != null) {
				fileList.addAll(Arrays.asList(files));
			}
		}

		return fileList;
	}

}
