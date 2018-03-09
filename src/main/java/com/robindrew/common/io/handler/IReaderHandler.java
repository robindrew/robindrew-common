package com.robindrew.common.io.handler;

import java.io.Reader;

public interface IReaderHandler<R> {

	R handle(Reader reader) throws Exception;

}
