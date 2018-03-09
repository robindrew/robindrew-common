package com.robindrew.common.codec;

import java.io.File;
import java.io.InputStream;

public interface IEncoder {

	byte[] encodeToBytes(byte[] data);

	byte[] encodeToBytes(String text);

	byte[] encodeToBytes(InputStream input);

	byte[] encodeToBytes(File file);
}
