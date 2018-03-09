package com.robindrew.common.text.parser;

import java.math.BigInteger;

public class BigIntegerParser extends ObjectParser<BigInteger> {

	@Override
	protected BigInteger parseObject(String text) {
		return new BigInteger(text);
	}

}
