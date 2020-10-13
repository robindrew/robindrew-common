package com.robindrew.common.io.file.reference;

import java.io.File;

import com.robindrew.common.io.persister.StringParserPersister;
import com.robindrew.common.text.parser.IStringParser;

public class StringParserFileReference<V> extends CachedFileReference<V> {

	public StringParserFileReference(File file, IStringParser<V> parser) {
		super(file, new StringParserPersister<V>(parser));
	}

}
