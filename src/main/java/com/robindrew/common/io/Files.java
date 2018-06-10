package com.robindrew.common.io;

import static com.google.common.base.Charsets.UTF_8;
import static com.robindrew.common.codec.MessageDigestEncoder.MD5;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.jetty.io.RuntimeIOException;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.CharStreams;
import com.google.common.io.LineProcessor;
import com.google.common.io.PatternFilenameFilter;
import com.robindrew.common.codec.MessageDigestEncoder;
import com.robindrew.common.util.Java;
import com.robindrew.common.util.SystemProperties;
import com.robindrew.common.util.Threads;

public class Files {

	public static final FileFilter ACCEPT_ALL = file -> true;
	public static final FileFilter ACCEPT_NONE = file -> false;
	public static final Charset DEFAULT_CHARSET = Charsets.UTF_8;
	public static final String DEFAULT_LINE_SEPARATOR = Java.getLineSeparator();

	/**
	 * Creates a temporary file that is deleted automatically when the JVM exists.
	 * @return the temporary file.
	 */
	public static final File createTempFile() {
		try {
			String prefix = System.nanoTime() + ".";
			File file = File.createTempFile(prefix, ".java.tmp");
			file.deleteOnExit();
			return file;
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	public static final ByteSource asByteSource(File file) {
		return com.google.common.io.Files.asByteSource(file);
	}

	public static final ByteSource asByteSource(String fileName) {
		File file = new File(fileName);
		return asByteSource(file);
	}

	public static final ByteSink asByteSink(File file) {
		return com.google.common.io.Files.asByteSink(file);
	}

	public static final ByteSink asByteSink(String fileName) {
		File file = new File(fileName);
		return asByteSink(file);
	}

	public static final CharSource asCharSource(String fileName) {
		File file = new File(fileName);
		return asCharSource(file);
	}

	public static final CharSource asCharSource(File file) {
		return com.google.common.io.Files.asCharSource(file, UTF_8);
	}

	public static final CharSink asCharSink(String fileName) {
		File file = new File(fileName);
		return asCharSink(file);
	}

	public static final CharSink asCharSink(File file) {
		return com.google.common.io.Files.asCharSink(file, UTF_8);
	}

	public static String readToString(File file) {
		try {
			return asCharSource(file).read();
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	public static String readToString(String filename) {
		return readToString(new File(filename));
	}

	public static void writeFromString(File file, String text) {
		try {
			asCharSink(file).write(text);
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	public static byte[] readToBytes(File file) {
		try {
			return asByteSource(file).read();
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	public static void writeFromBytes(File file, byte[] bytes) {
		try {
			asByteSink(file).write(bytes);
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	public static List<File> listFiles(File directory, boolean recursive) {
		return listFiles(directory, recursive, ACCEPT_ALL);
	}

	public static List<File> listFiles(File directory, boolean recursive, String filenamePattern) {
		return listFiles(directory, recursive, new PatternFileFilter(filenamePattern));
	}

	public static List<File> listFiles(File directory, boolean recursive, FileFilter filter) {
		if (filter == null) {
			throw new NullPointerException("filter");
		}
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException("Not a directory: " + directory);
		}

		List<File> list = new ArrayList<>();

		if (!recursive) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (filter == ACCEPT_ALL || filter.accept(file)) {
						list.add(file);
					}
				}
			}
		}

		else {
			try {
				Path path = directory.toPath();
				java.nio.file.Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
						if (filter == ACCEPT_ALL || filter.accept(file.toFile())) {
							list.add(file.toFile());
						}
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (IOException ioe) {
				throw new RuntimeIOException(ioe);
			}
		}
		return list;
	}

	public static List<File> listContents(File directory) {
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException("Not a directory: " + directory);
		}

		File[] files = directory.listFiles();
		if (files == null) {
			return Collections.emptyList();
		}
		return Arrays.asList(files);
	}

	public static List<File> listContents(File directory, Pattern pattern) {
		return listContents(directory, new PatternFilenameFilter(pattern));
	}

	public static List<File> listContents(File directory, FileFilter filter) {
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException("Not a directory: " + directory);
		}
		if (filter == null) {
			throw new NullPointerException("filter");
		}

		File[] files = directory.listFiles();
		if (files == null) {
			return Collections.emptyList();
		}
		List<File> list = new ArrayList<>();
		for (File file : files) {
			if (filter.accept(file)) {
				list.add(file);
			}
		}
		return list;
	}

	public static List<File> listContents(File directory, FilenameFilter filter) {
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException("Not a directory: " + directory);
		}
		if (filter == null) {
			throw new NullPointerException("filter");
		}

		File[] files = directory.listFiles();
		if (files == null) {
			return Collections.emptyList();
		}
		List<File> list = new ArrayList<>();
		for (File file : files) {
			if (filter.accept(directory, file.getName())) {
				list.add(file);
			}
		}
		return list;
	}

	public static void writeFromStream(File file, InputStream input) {
		try (OutputStream output = new FileOutputStream(file)) {
			ByteStreams.copy(input, output);
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static void writeFromReader(File file, Reader reader, Charset charset) {
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), charset)) {
			CharStreams.copy(reader, writer);
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static void writeFromZipEntry(File file, ZipFile zipFile, ZipEntry zipEntry) {
		try (InputStream input = zipFile.getInputStream(zipEntry)) {
			try (OutputStream output = new FileOutputStream(file)) {
				ByteStreams.copy(input, output);
			}
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static void writeFromLines(File file, Iterable<String> lines) {
		try (BufferedWriter sink = new BufferedWriter(asCharSink(file).openStream())) {
			String newLine = SystemProperties.getLineSeparator();
			for (String line : lines) {
				sink.write(line);
				sink.write(newLine);
			}
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static <R> R readFromLines(File file, LineProcessor<R> processor) {
		try {
			return asCharSource(file).readLines(processor);
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static String getRelativePath(File parent, File child) {
		if (parent.equals(child)) {
			throw new IllegalArgumentException("parent: " + parent + ", child: " + child);
		}

		String parentPath = parent.getAbsolutePath();
		String childPath = child.getAbsolutePath();
		if (!childPath.startsWith(parentPath)) {
			throw new IllegalArgumentException("parent: " + parent + ", child: " + child);
		}

		String relativePath = childPath.substring(parentPath.length());
		if (relativePath.startsWith("/") || relativePath.startsWith("\\")) {
			relativePath = relativePath.substring(1);
		}
		return relativePath;
	}

	public static void deleteFile(File file) {
		while (file.exists()) {
			if (!file.delete()) {
				Threads.sleep(5, MILLISECONDS);
			}
		}
	}

	public static void deleteContents(File directory, boolean includeDirectory) {
		if (!directory.exists()) {
			return;
		}
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException("not a directory: " + directory);
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				deleteContents(directory, true);
			} else {
				deleteFile(file);
			}
		}

		if (includeDirectory) {
			deleteFile(directory);
		}
	}

	public static byte[] readToMd5(File file) {
		return new MessageDigestEncoder(MD5).encodeToBytes(file);
	}

}
