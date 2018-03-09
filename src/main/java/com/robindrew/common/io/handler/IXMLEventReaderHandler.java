package com.robindrew.common.io.handler;

import javax.xml.stream.XMLEventReader;

public interface IXMLEventReaderHandler<R> {

	R handle(XMLEventReader reader) throws Exception;

}
