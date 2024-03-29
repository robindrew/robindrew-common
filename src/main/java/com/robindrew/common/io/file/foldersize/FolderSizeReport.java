package com.robindrew.common.io.file.foldersize;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

public class FolderSizeReport extends SimpleFileVisitor<Path> {

	private static final Logger log = LoggerFactory.getLogger(FolderSizeReport.class);

	private final Map<Path, FolderSize> folderSizeMap = new LinkedHashMap<>();
	private FolderSize rootFolder = null;
	private FolderSize currentFolder = null;
	private int fileCount = 0;
	private int folderCount = 0;

	public FolderSize getRootFolder() {
		return rootFolder;
	}

	public long getSize() {
		return rootFolder.getSize();
	}

	public int getFileCount() {
		return fileCount;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

	public int getFolderCount() {
		return folderCount;
	}

	public void setFolderCount(int folderCount) {
		this.folderCount = folderCount;
	}

	public List<Path> getFolderList() {
		return ImmutableList.copyOf(folderSizeMap.keySet());
	}

	public List<FolderSize> getFolderSizeList() {
		return ImmutableList.copyOf(folderSizeMap.values());
	}

	public List<FolderSize> getFolderSizeList(FolderSizeFilters filters) {
		List<FolderSize> list = new ArrayList<>(folderSizeMap.values());
		Collections.sort(list);

		int maxDepth = filters.getMaxFolderDepth();
		if (maxDepth > 0) {
			List<FolderSize> newList = new ArrayList<>();
			for (FolderSize element : list) {
				if (element.getDepth() <= maxDepth) {
					newList.add(element);
				}
			}
			list = newList;
		}

		int maxCount = filters.getMaxFolderCount();
		if (maxCount > 0 && maxCount < list.size()) {
			list = list.subList(0, maxCount);
		}
		return ImmutableList.copyOf(list);
	}

	@Override
	public FileVisitResult preVisitDirectory(Path folder, BasicFileAttributes attrs) throws IOException {
		final FolderSize folderSize;

		// Root folder
		if (currentFolder == null) {
			folderSize = new FolderSize(folder);
			rootFolder = folderSize;
		}

		// Sub folder
		else {
			currentFolder.newFolder();
			folderSize = new FolderSize(folder, currentFolder);
			folderCount++;
		}

		folderSizeMap.put(folder, folderSize);
		currentFolder = folderSize;

		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		if (attrs.isRegularFile()) {
			currentFolder.newFile(attrs.size());
			fileCount++;
		}
		// We don't handle or record non-regular files yet (sym links, etc)
		return CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path folder, IOException exc) throws IOException {
		FolderSize folderSize = folderSizeMap.get(folder);
		currentFolder = folderSize.getParent().orElse(null);
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path path, IOException e) throws IOException {
		if (e instanceof AccessDeniedException) {
			log.warn("Access denied to path: " + path);
		} else {
			log.warn("Failed to visit path: " + path, e);
		}
		return CONTINUE;
	}

}
