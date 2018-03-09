package com.robindrew.common.text.parser;

import java.math.BigDecimal;

public class BigDecimalParser extends ObjectParser<BigDecimal> {

	@Override
	protected BigDecimal parseObject(String text) {
		return new BigDecimal(text);
	}

}
