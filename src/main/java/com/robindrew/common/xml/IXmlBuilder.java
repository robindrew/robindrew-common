package com.robindrew.common.xml;

public interface IXmlBuilder {

	String toXml();

	IXmlBuilder declaration();

	IXmlBuilder open(String name);

	IXmlBuilder element(String name);

	IXmlBuilder element(String name, Object value);

	IXmlBuilder empty(String name);

	IXmlBuilder close();

	IXmlBuilder close(String value);

	IXmlBuilder attribute(String name, Object value);

	IXmlBuilder attribute(String name, int value);

	IXmlBuilder attribute(String name, long value);

}
