package com.robindrew.common.io.file.foldersize;

public class FolderSizeFilters {

	private int maxFolderDepth = 0;
	private int maxFolderCount = 0;

	public int getMaxFolderDepth() {
		return maxFolderDepth;
	}

	public int getMaxFolderCount() {
		return maxFolderCount;
	}

	public void setMaxFolderDepth(int maxFolderDepth) {
		if (maxFolderDepth < 0) {
			throw new IllegalArgumentException("maxFolderDepth=" + maxFolderDepth);
		}
		this.maxFolderDepth = maxFolderDepth;
	}

	public void setMaxFolderCount(int maxFolderCount) {
		if (maxFolderCount < 0) {
			throw new IllegalArgumentException("maxFolderCount=" + maxFolderCount);
		}
		this.maxFolderCount = maxFolderCount;
	}

}
