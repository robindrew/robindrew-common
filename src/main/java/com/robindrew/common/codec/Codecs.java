package com.robindrew.common.codec;

import java.util.Base64;

public class Codecs {

	public static String decodeBase64(String text) {
		return new String(Base64.getDecoder().decode(text));
	}

}
