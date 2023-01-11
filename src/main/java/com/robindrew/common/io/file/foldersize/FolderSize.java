package com.robindrew.common.io.file.foldersize;

import java.nio.file.Path;
import java.util.Optional;

public class FolderSize implements Comparable<FolderSize> {

	private final Path path;
	private final Optional<FolderSize> parent;
	private final int depth;
	private long size = 0;

	private int fileCount = 0;
	private int symLinkCount = 0;
	private int folderCount = 0;

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

	public int getDepth() {
		return depth;
	}

	public int getFileCount() {
		return fileCount;
	}

	public int getSubFolderCount() {
		return folderCount;
	}

	public int getSymLinkCount() {
		return symLinkCount;
	}

	void newFolder() {
		this.folderCount++;
	}

	void newSymLink() {
		this.symLinkCount++;
	}

	void newFile(long size) {
		newFile(size, true);
	}

	void newFile(long size, boolean incrementFiles) {
		if (size < 0) {
			throw new IllegalArgumentException("size");
		}
		this.size += size;
		if (incrementFiles) {
			this.fileCount++;
		}

		if (parent.isPresent()) {
			parent.get().newFile(size, false);
		}
	}

	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append("FolderSize[path=").append(path);
		text.append(",size=").append(size);
		if (depth > 0) {
			text.append(",depth=").append(depth);
		}
		if (fileCount > 0) {
			text.append(",fileCount=").append(fileCount);
		}
		if (symLinkCount > 0) {
			text.append(",symLinkCount=").append(symLinkCount);
		}
		if (folderCount > 0) {
			text.append(",folderCount=").append(folderCount);
		}
		text.append("]");
		return text.toString();
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

}
