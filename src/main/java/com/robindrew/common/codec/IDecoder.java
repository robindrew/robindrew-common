package com.robindrew.common.codec;

public interface IDecoder {

	String decodeToString(byte[] data);

	byte[] decodeToBytes(byte[] data);

}
