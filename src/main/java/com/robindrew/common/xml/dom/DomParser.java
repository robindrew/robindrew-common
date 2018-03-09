package com.robindrew.common.xml.dom;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.robindrew.common.util.Java;

public class DomParser implements IDomParser {

	private final DocumentBuilder builder;

	public DomParser() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			this.builder = factory.newDocumentBuilder();
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public IDomElement parse(String xml) {
		try {
			Document document = builder.parse(new InputSource(new StringReader(xml)));
			return new DomElement(document.getDocumentElement());
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public IDomElement parse(Reader reader) {
		try {
			Document document = builder.parse(new InputSource(reader));
			return new DomElement(document.getDocumentElement());
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public IDomElement parse(InputStream input) {
		try {
			Document document = builder.parse(new InputSource(input));
			return new DomElement(document.getDocumentElement());
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

}
