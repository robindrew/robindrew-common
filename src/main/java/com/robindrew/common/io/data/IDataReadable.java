package com.robindrew.common.io.data;

import java.io.IOException;

public interface IDataReadable<O> {

	O readObject(IDataReader reader) throws IOException;

}
