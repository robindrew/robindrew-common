package com.robindrew.common.codec;

public interface IStringDecoder extends IObjectDecoder {

	String decodeToString(CharSequence text);

}
