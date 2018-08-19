package com.robindrew.common.template.velocity;

import java.nio.charset.Charset;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.LogChute;

import com.google.common.base.Charsets;
import com.google.common.base.Supplier;
import com.robindrew.common.template.ITemplateLocator;

public class VelocityTemplateLocatorSupplier implements Supplier<ITemplateLocator> {

	private Charset inputEncoding = Charsets.UTF_8;
	private Charset outputEncoding = Charsets.UTF_8;
	private String path = null;
	private boolean caching = true;
	private String extension = "";
	private LogChute logChute = new Slf4jLogChute();
	private boolean strict = false;

	public String getPath() {
		return path;
	}

	public boolean isCaching() {
		return caching;
	}

	public String getExtension() {
		return extension;
	}

	public LogChute getLogChute() {
		return logChute;
	}

	public Charset getInputEncoding() {
		return inputEncoding;
	}

	public Charset getOutputEncoding() {
		return outputEncoding;
	}

	public String getFileResourceLoaderPath() {
		return path;
	}

	public boolean isFileResourceLoaderCaching() {
		return caching;
	}

	public VelocityTemplateLocatorSupplier setLogChute(LogChute logChute) {
		if (logChute == null) {
			throw new NullPointerException("logChute");
		}
		this.logChute = logChute;
		return this;
	}

	public VelocityTemplateLocatorSupplier setExtension(String extension) {
		if (extension.isEmpty()) {
			throw new IllegalArgumentException("extension is empty");
		}
		this.extension = extension;
		return this;
	}

	public VelocityTemplateLocatorSupplier setPath(String path) {
		if (path.isEmpty()) {
			throw new IllegalArgumentException("path is empty");
		}
		this.path = path;
		return this;
	}

	public VelocityTemplateLocatorSupplier setCaching(boolean caching) {
		this.caching = caching;
		return this;
	}

	public VelocityTemplateLocatorSupplier setInputEncoding(Charset charset) {
		if (charset == null) {
			throw new NullPointerException("charset");
		}
		inputEncoding = charset;
		return this;
	}

	public VelocityTemplateLocatorSupplier setOutputEncoding(Charset charset) {
		if (charset == null) {
			throw new NullPointerException("charset");
		}
		outputEncoding = charset;
		return this;
	}

	@Override
	public ITemplateLocator get() {
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.INPUT_ENCODING, inputEncoding.name());
		engine.setProperty(RuntimeConstants.OUTPUT_ENCODING, outputEncoding.name());
		engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, logChute);
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
		engine.setProperty("class.resource.loader.class", VelocityResourceLoader.class.getName());
		if (path != null) {
			engine.setProperty("class.resource.loader.path", path);
		}
		engine.setProperty("class.resource.loader.caching", caching);
		if (strict) {
			engine.setProperty("runtime.references.strict", strict);
		}
		if (!extension.isEmpty()) {
			engine.setProperty("class.resource.loader.extension", extension);
		}
		engine.setProperty("velocimacro.library", "");
		engine.init();
		return new VelocityTemplateLocator(engine);
	}

}
