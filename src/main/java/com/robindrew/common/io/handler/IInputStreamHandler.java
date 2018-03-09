package com.robindrew.common.io.handler;

import java.io.InputStream;

public interface IInputStreamHandler<R> {

	R handle(InputStream input) throws Exception;

}
