package com.robindrew.common.io.file.foldersize;

import java.nio.file.Path;
import java.util.Optional;

public class FolderSize implements Comparable<FolderSize> {

	private final Path path;
	private final Optional<FolderSize> parent;
	private final int depth;
	private volatile long size = 0;

	public FolderSize(Path path) {
		if (path == null) {
			throw new NullPointerException("path");
		}
		this.path = path;
		this.parent = Optional.empty();
		this.depth = 0;
	}

	public FolderSize(Path path, FolderSize parent) {
		if (path == null) {
			throw new NullPointerException("path");
		}
		if (parent == null) {
			throw new NullPointerException("parent");
		}
		this.path = path;
		this.parent = Optional.of(parent);
		this.depth = 1 + parent.getDepth();
	}

	public Path getPath() {
		return path;
	}

	public Optional<FolderSize> getParent() {
		return parent;
	}

	public long getSize() {
		return size;
	}

	public void increment(long size) {
		if (size < 0) {
			throw new IllegalArgumentException("size");
		}
		this.size += size;

		if (parent.isPresent()) {
			parent.get().increment(size);
		}
	}

	@Override
	public String toString() {
		return "FolderSize[path=" + path + ",size=" + size + "]";
	}

	@Override
	public int compareTo(FolderSize that) {
		if (this.size > that.size) {
			return -1;
		}
		if (this.size < that.size) {
			return 1;
		}
		return this.path.compareTo(that.path);
	}

	public int getDepth() {
		return depth;
	}
}
