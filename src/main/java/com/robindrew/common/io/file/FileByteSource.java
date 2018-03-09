package com.robindrew.common.io.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.google.common.io.ByteSource;
import com.robindrew.common.io.stream.LoggedInputStream;

public class FileByteSource extends ByteSource {

	private final File file;
	private String zipFilename = null;

	public FileByteSource(File file) {
		if (file == null) {
			throw new NullPointerException("file");
		}
		this.file = file;
	}

	public FileByteSource setZipFilename(String filename) {
		if (filename.isEmpty()) {
			throw new IllegalArgumentException("filename is empty");
		}
		zipFilename = filename;
		return this;
	}

	@SuppressWarnings("resource")
	@Override
	public InputStream openStream() throws IOException {
		InputStream input = new FileInputStream(file);
		if (isGzip(file)) {
			input = new GZIPInputStream(input);
		}
		if (isZip(file)) {
			ZipInputStream zip = new ZipInputStream(input);
			while (true) {
				ZipEntry entry = zip.getNextEntry();
				if (entry == null) {
					if (zipFilename != null) {
						throw new IllegalStateException("No entries in zip with name: '" + zipFilename + "'");
					}
					throw new IllegalStateException("No entries in zip");
				}
				if (zipFilename == null || zipFilename.equals(entry.getName())) {
					input = zip;
					break;
				}
			}
			input = zip;
		}
		input = new LoggedInputStream(file.getName(), input);
		return input;
	}

	protected boolean isGzip(File file) {
		return file.getName().toLowerCase().endsWith(".gz");
	}

	protected boolean isZip(File file) {
		return file.getName().toLowerCase().endsWith(".zip");
	}

}
