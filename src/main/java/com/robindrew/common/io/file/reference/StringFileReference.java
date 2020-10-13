package com.robindrew.common.io.file.reference;

import java.io.File;

import com.robindrew.common.io.persister.StringPersister;

public class StringFileReference extends CachedFileReference<String> {

	public StringFileReference(File file) {
		super(file, new StringPersister());
	}

}
