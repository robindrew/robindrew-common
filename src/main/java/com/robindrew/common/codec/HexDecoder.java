package com.robindrew.common.codec;

import org.apache.commons.codec.binary.Hex;

public class HexDecoder implements IDecoder {

	@Override
	public String decodeToString(byte[] data) {
		return Hex.encodeHexString(data);
	}

	@Override
	public byte[] decodeToBytes(byte[] data) {
		throw new UnsupportedOperationException();
	}

}
