package com.robindrew.common.maven.repository;

import static com.robindrew.common.text.Strings.bytes;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.util.Arrays.asList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.base.Stopwatch;
import com.google.common.io.ByteStreams;
import com.robindrew.common.maven.IMavenArtifact;
import com.robindrew.common.maven.IMavenFileType;
import com.robindrew.common.maven.IMavenRepository;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Java;

public class HttpRepository implements IMavenRepository {

	private static final Logger log = LoggerFactory.getLogger(HttpRepository.class);

	public static final List<String> DEFAULT_MAVEN_URLS = asList("http://central.maven.org/maven2", "http://dl.bintray.com/typesafe/maven-releases", "http://dl.bintray.com/mockito/maven");

	private final Set<String> mavenUrls;

	public HttpRepository(Collection<? extends String> mavenUrls) {
		Check.notEmpty("mavenUrls", mavenUrls);
		this.mavenUrls = new LinkedHashSet<>(mavenUrls);
	}

	protected HttpURLConnection connect(String url) throws MalformedURLException, IOException {
		return (HttpURLConnection) new URL(url).openConnection();
	}

	protected String getUrl(String mavenUrl, IMavenArtifact artifact, IMavenFileType type) {
		String path = type.getPath(artifact);
		return mavenUrl + "/" + path;
	}

	@Override
	public boolean canGet(IMavenArtifact artifact, IMavenFileType type) {
		for (String mavenUrl : mavenUrls) {
			try {
				String url = getUrl(mavenUrl, artifact, type);
				HttpURLConnection connection = connect(url);
				connection.setRequestMethod("HEAD");
				if (connection.getResponseCode() == HTTP_OK) {
					return true;
				}
			} catch (Exception e) {
				log.warn("Exception checking url: " + mavenUrl + " for artifact: " + artifact, e);
			}
		}
		return false;
	}

	@Override
	public boolean canPut(IMavenArtifact artifact, IMavenFileType type) {
		return false;
	}

	@Override
	public byte[] get(IMavenArtifact artifact, IMavenFileType type) {
		Exception latest = null;
		for (String mavenUrl : mavenUrls) {
			try {
				String url = getUrl(mavenUrl, artifact, type);
				log.info("[Downloading] {}", url);
				HttpURLConnection connection = connect(url);

				Stopwatch timer = Stopwatch.createStarted();

				try (InputStream input = connection.getInputStream()) {
					byte[] bytes = ByteStreams.toByteArray(input);
					timer.stop();
					log.info("[Downloaded] {} in {} ({})", url, timer, bytes(bytes));
					return bytes;
				}

			} catch (Exception e) {
				latest = e;
			}
		}
		throw Java.propagate(latest);
	}

	@Override
	public void put(IMavenArtifact artifact, IMavenFileType type, byte[] bytes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<File> getLocalPath(IMavenArtifact artifact, IMavenFileType type) {
		return Optional.absent();
	}

}
