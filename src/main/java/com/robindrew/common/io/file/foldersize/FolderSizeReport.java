package com.robindrew.common.io.file.foldersize;

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
	private volatile FolderSize currentFolder = null;
	private volatile long currentSize = 0;

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
		FolderSize folderSize;
		if (currentFolder != null) {
			folderSize = new FolderSize(folder, currentFolder);
		} else {
			folderSize = new FolderSize(folder);
		}
		folderSizeMap.put(folder, folderSize);

		// Reset
		currentSize = 0;
		currentFolder = folderSize;

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		currentSize += attrs.size();
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path folder, IOException exc) throws IOException {

		FolderSize folderSize = folderSizeMap.get(folder);
		folderSize.increment(currentSize);

		// Reset
		currentSize = 0;
		currentFolder = folderSize.getParent().orElse(null);

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path path, IOException e) throws IOException {
		if (e instanceof AccessDeniedException) {
			log.warn("Access denied to path: " + path);
		} else {
			log.warn("Unable to visit path: " + path, e);
		}
		return FileVisitResult.CONTINUE;
	}

}