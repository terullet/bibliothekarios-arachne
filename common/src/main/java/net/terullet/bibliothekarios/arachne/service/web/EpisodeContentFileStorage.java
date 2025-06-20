package net.terullet.bibliothekarios.arachne.service.web;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class EpisodeContentFileStorage implements EpisodeContentStorage {
	private final Path storageRoot;

	public EpisodeContentFileStorage(Path storageRoot) {
		this.storageRoot = storageRoot;
	}

	public final Path getStorageRoot() {
		return this.storageRoot;
	}

	private Path generatePathFromPostId(long postId) {
		return this.storageRoot.resolve(String.format("%02x/%02x/%016x.txt.gz", (postId >> 16) & 0xff, (postId >> 8) & 0xff, postId));
	}

	public void saveEpisodeContent(long postId, List<String> paragraphs) throws IOException {
		Path path = this.generatePathFromPostId(postId);
		if (!Files.exists(path.getParent())) {
			Files.createDirectories(path.getParent());
		}
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(path.toFile(), false))), StandardCharsets.UTF_8))) {
			for (String p : paragraphs) {
				bw.write(p);
			}
		}
	}

	public List<String> loadEpisodeContent(long postId) throws IOException {
		Path path = this.generatePathFromPostId(postId);
		List<String> paragraphs = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new BufferedInputStream(new FileInputStream(path.toFile()))), StandardCharsets.UTF_8))) {
			String str;
			while ((str = br.readLine()) != null) {
				paragraphs.add(str);
			}
		}
		return paragraphs;
	}
}
