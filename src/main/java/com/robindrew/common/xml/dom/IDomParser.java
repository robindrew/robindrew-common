package com.robindrew.common.xml.dom;

import java.io.InputStream;
import java.io.Reader;

public interface IDomParser {

	IDomElement parse(String xml);

	IDomElement parse(Reader reader);

	IDomElement parse(InputStream input);

}
