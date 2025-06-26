package net.terullet.bibliothekarios.arachne.service.web;

import java.util.List;

public interface EpisodeContentStorage {
	void saveEpisodeContent(long postId, List<String> paragraphs) throws Exception;
	List<String> loadEpisodeContent(long postId) throws Exception;
}
