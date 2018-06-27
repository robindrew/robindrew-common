package com.robindrew.common.service.component.jetty.handler.page.system;

import java.io.File;
import java.nio.file.Path;

import com.robindrew.common.text.Strings;

public class FileRootView implements Comparable<FileRootView> {

	private final Path path;
	private final File file;

	public FileRootView(Path path) {
		this.path = path;
		this.file = path.toFile();
	}

	public Path getPath() {
		return path;
	}

	public File getFile() {
		return file;
	}

	public String getName() {
		return path.toString();
	}

	public String getPercent() {
		long free = file.getUsableSpace();
		long total = file.getTotalSpace();
		if (total == 0) {
			return "-";
		}
		return Strings.percent(total - free, total);
	}

	public String getCapacity() {
		long total = file.getTotalSpace();
		if (total == 0) {
			return "-";
		}
		return Strings.bytes(total);
	}

	public String getFree() {
		long total = file.getTotalSpace();
		long free = file.getFreeSpace();
		if (total == 0) {
			return "-";
		}
		return Strings.bytes(free);
	}

	public String getUsed() {
		long total = file.getTotalSpace();
		long free = file.getFreeSpace();
		if (total == 0) {
			return "-";
		}
		return Strings.bytes(total - free);
	}

	@Override
	public int compareTo(FileRootView that) {
		return this.getPath().compareTo(that.getPath());
	}
}
