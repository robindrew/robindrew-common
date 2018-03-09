package com.robindrew.common.maven.repository;

import static com.robindrew.common.text.Strings.bytes;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.robindrew.common.io.Files;
import com.robindrew.common.maven.IMavenArtifact;
import com.robindrew.common.maven.IMavenFileType;
import com.robindrew.common.maven.IMavenRepository;
import com.robindrew.common.util.Check;

public class DirectoryRepository implements IMavenRepository {

	private static final Logger log = LoggerFactory.getLogger(DirectoryRepository.class);

	private final File directory;

	public DirectoryRepository(File directory) {
		this.directory = Check.existsDirectory("directory", directory);
	}

	public File getFile(IMavenArtifact artifact, IMavenFileType type) {
		String path = type.getPath(artifact);
		return new File(directory, path);
	}

	@Override
	public boolean canGet(IMavenArtifact artifact, IMavenFileType type) {
		return getFile(artifact, type).exists();
	}

	@Override
	public boolean canPut(IMavenArtifact artifact, IMavenFileType type) {
		return true;
	}

	@Override
	public byte[] get(IMavenArtifact artifact, IMavenFileType type) {
		File file = getFile(artifact, type);
		byte[] bytes = Files.readToBytes(file);
		log.info("[File] {} ({})", file, bytes(bytes));
		return bytes;
	}

	@Override
	public void put(IMavenArtifact artifact, IMavenFileType type, byte[] bytes) {
		File file = getFile(artifact, type);
		file.getParentFile().mkdirs();
		Files.writeFromBytes(file, bytes);
	}

	@Override
	public Optional<File> getLocalPath(IMavenArtifact artifact, IMavenFileType type) {
		File file = getFile(artifact, type);
		if (!file.exists()) {
			return Optional.absent();
		}
		return Optional.of(file);
	}

}
