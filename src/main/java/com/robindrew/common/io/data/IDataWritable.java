package com.robindrew.common.io.data;

import java.io.IOException;

public interface IDataWritable<O> {

	void writeObject(IDataWriter writer, O instance) throws IOException;

}
