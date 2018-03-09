package com.robindrew.common.io.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.google.common.io.ByteSink;
import com.robindrew.common.io.stream.LoggedOutputStream;

public class FileByteSink extends ByteSink {

	private final File file;
	private String zipFilename = null;

	public FileByteSink(File file) {
		if (file == null) {
			throw new NullPointerException("file");
		}
		this.file = file;
	}

	public FileByteSink setZipFilename(String filename) {
		if (filename.isEmpty()) {
			throw new IllegalArgumentException("filename is empty");
		}
		zipFilename = filename;
		return this;
	}

	@SuppressWarnings("resource")
	@Override
	public OutputStream openStream() throws IOException {
		OutputStream output = new FileOutputStream(file);
		if (isGzip(file)) {
			output = new GZIPOutputStream(output);
		}
		if (zipFilename != null && isZip(file)) {
			ZipOutputStream zip = new ZipOutputStream(output);
			ZipEntry entry = new ZipEntry(zipFilename);
			zip.putNextEntry(entry);
			output = zip;
		}
		output = new LoggedOutputStream(file.getName(), output);
		return output;
	}

	protected boolean isGzip(File file) {
		return file.getName().toLowerCase().endsWith(".gz");
	}

	protected boolean isZip(File file) {
		return file.getName().toLowerCase().endsWith(".zip");
	}

}
