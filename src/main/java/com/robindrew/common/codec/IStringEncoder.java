package com.robindrew.common.codec;

public interface IStringEncoder extends IObjectEncoder {

	String encodeToString(CharSequence text);

}
