package com.robindrew.common.io;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.robindrew.common.util.Check;

public class RelativeFile implements Comparable<RelativeFile> {

	public static Set<RelativeFile> fromDirectory(File parent) {
		Set<RelativeFile> set = new LinkedHashSet<>();

		String parentPath = parent.getAbsolutePath();

		List<File> children = Files.listFiles(parent, true);
		for (File child : children) {
			String childPath = child.getAbsolutePath();
			String path = childPath.substring(parentPath.length() + 1);
			RelativeFile file = new RelativeFile(parent, path);
			set.add(file);
		}

		return set;
	}

	public static RelativeFile fromFile(File parentDir, File child) {
		if (child.equals(parentDir)) {
			throw new IllegalArgumentException("files are the same");
		}

		String childPath = child.getAbsolutePath();
		String parentPath = parentDir.getAbsolutePath();
		if (!childPath.equals(parentPath)) {
			throw new IllegalArgumentException(child + " is not a descendant of " + parentDir);
		}

		String path = childPath.substring(parentPath.length() + 1);
		return new RelativeFile(parentDir, path);
	}

	private final String childPath;
	private final File parentDir;

	public RelativeFile(File parentDir, String childPath) {
		this.childPath = Check.notEmpty("childPath", childPath).replace('\\', '/');
		this.parentDir = Check.directory("parentDir", parentDir);
	}

	public File getChildFile() {
		return new File(parentDir, childPath);
	}

	public String getChildPath() {
		return childPath;
	}

	public File getParentDir() {
		return parentDir;
	}

	@Override
	public String toString() {
		return childPath;
	}

	@Override
	public int hashCode() {
		return 1999 * childPath.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object instanceof RelativeFile) {
			RelativeFile that = (RelativeFile) object;
			if (this.getChildPath().equals(that.getChildPath())) {
				return this.getParentDir().equals(that.getParentDir());
			}
		}
		return false;
	}

	@Override
	public int compareTo(RelativeFile that) {
		CompareToBuilder compare = new CompareToBuilder();
		compare.append(this.getChildPath(), that.getChildPath());
		compare.append(this.getParentDir(), that.getParentDir());
		return compare.toComparison();
	}

}
