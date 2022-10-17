package com.robindrew.common.codec;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collection;

public class Md5FileEncoder extends Md5Encoder {

	public static final String MD5_ALGORITHM = "MD5";
	public static final int DEFAULT_BUFFER_SIZE = 1000000;

	private int bufferSize = DEFAULT_BUFFER_SIZE;

	public int getBufferSize() {
		return bufferSize;
	}

	@Override
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public byte[] encodeToBytes(File file) {
		return encodeFiles(Arrays.asList(file));
	}

	public byte[] encodeFiles(Collection<? extends File> files) {
		MessageDigest digest = getDigest();
		synchronized (digest) {

			for (File file : files) {
				digestFile(digest, file);
			}

			// Binary hash
			return digest.digest();
		}

	}

	private void digestFile(MessageDigest digest, File file) {
		byte[] buffer = getBuffer();
		try {
			try (InputStream input = new FileInputStream(file)) {
				while (true) {
					int length = input.read(buffer);
					if (length == -1) {
						break;
					}
					digest.update(buffer, 0, length);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Unable to MD5 " + file, e);
		}
	}

}
