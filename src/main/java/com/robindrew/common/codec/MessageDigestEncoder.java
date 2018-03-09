package com.robindrew.common.codec;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import com.google.common.base.Charsets;
import com.robindrew.common.util.Java;

public class MessageDigestEncoder implements IEncoder {

	public static final String MD2 = "MD2";
	public static final String MD5 = "MD5";

	public static final String SHA_1 = "SHA-1";
	public static final String SHA_256 = "SHA-256";
	public static final String SHA_384 = "SHA-384";
	public static final String SHA_512 = "SHA-512";

	public static final MessageDigest getDigest(String algorithm) {
		if (algorithm.isEmpty()) {
			throw new IllegalArgumentException("algorithm is empty");
		}
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	private final MessageDigest digest;
	private byte[] buffer = new byte[10000000];

	public MessageDigestEncoder(String algorithm) {
		digest = getDigest(algorithm);
	}

	public MessageDigest getDigest() {
		return digest;
	}

	public void setBufferSize(int size) {
		if (size < 1) {
			throw new IllegalArgumentException("size=" + size);
		}
		buffer = new byte[size];
	}

	protected byte[] getBuffer() {
		return buffer;
	}

	@Override
	public byte[] encodeToBytes(String text) {
		return encodeToBytes(text.getBytes(Charsets.UTF_8));
	}

	@Override
	public byte[] encodeToBytes(byte[] data) {
		try {
			synchronized (getDigest()) {
				return getDigest().digest(data);
			}
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	private byte[] encode(InputStream input) throws Exception {
		synchronized (getDigest()) {
			while (true) {
				int read = input.read(getBuffer());
				if (read == -1) {
					break;
				}
				getDigest().update(getBuffer(), 0, read);
			}
			return getDigest().digest();
		}
	}

	@Override
	public byte[] encodeToBytes(InputStream input) {
		try {
			return encode(input);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public byte[] encodeToBytes(File file) {
		try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {
			return encode(input);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

}
