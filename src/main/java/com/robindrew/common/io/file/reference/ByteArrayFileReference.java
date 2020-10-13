package com.robindrew.common.io.file.reference;

import java.io.File;

import com.robindrew.common.io.persister.ByteArrayPersister;

public class ByteArrayFileReference extends CachedFileReference<byte[]> {

	public ByteArrayFileReference(File file) {
		super(file, new ByteArrayPersister());
	}

}
