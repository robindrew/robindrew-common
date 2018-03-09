package com.robindrew.common.text.parser;

import java.io.File;

public class FileParser extends ObjectParser<File> {

	@Override
	protected File parseObject(String text) {
		return new File(text);
	}

}
