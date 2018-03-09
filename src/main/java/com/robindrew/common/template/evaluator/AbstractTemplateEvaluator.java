package com.robindrew.common.template.evaluator;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;

import com.google.common.base.Charsets;

public abstract class AbstractTemplateEvaluator implements ITemplateEvaluator {

	private Charset charset = Charsets.UTF_8;

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		if (charset == null) {
			throw new NullPointerException("charset");
		}
		this.charset = charset;
	}

	@Override
	public String evaluate(InputStream input, Map<String, Object> parameters) {
		Reader reader = new InputStreamReader(input, getCharset());
		return evaluate(reader, parameters);
	}

	@Override
	public void evaluate(InputStream input, OutputStream output, Map<String, Object> parameters) {
		Reader reader = new InputStreamReader(input, getCharset());
		Writer writer = new OutputStreamWriter(output, getCharset());
		evaluate(reader, writer, parameters);
	}
}
