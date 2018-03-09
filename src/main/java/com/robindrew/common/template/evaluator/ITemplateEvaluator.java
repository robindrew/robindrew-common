package com.robindrew.common.template.evaluator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

public interface ITemplateEvaluator {

	String evaluate(String template, Map<String, Object> parameters);

	String evaluate(Reader reader, Map<String, Object> parameters);

	String evaluate(InputStream input, Map<String, Object> parameters);

	void evaluate(Reader reader, Writer writer, Map<String, Object> parameters);

	void evaluate(InputStream input, OutputStream output, Map<String, Object> parameters);

}
