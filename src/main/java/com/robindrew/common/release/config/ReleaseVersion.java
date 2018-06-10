package com.robindrew.common.release.config;

import static com.robindrew.common.util.Check.notEmpty;

import org.simpleframework.xml.Element;

public class ReleaseVersion {

	@Element
	private String hash;
	@Element
	private String version;

	public ReleaseVersion() {
	}

	public ReleaseVersion(String hash, String version) {
		setHash(hash);
		setVersion(version);
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = notEmpty("hash", hash);;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = notEmpty("version", version);
	}

}
