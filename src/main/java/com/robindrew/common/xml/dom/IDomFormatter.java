package com.robindrew.common.xml.dom;

import com.robindrew.common.xml.IXmlBuilder;

public interface IDomFormatter {

	String format(String xml);

	String format(String xml, IXmlBuilder builder);

}
