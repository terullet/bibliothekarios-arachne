package net.terullet.bibliothekarios.arachne.repository.mapper.web;

import net.terullet.bibliothekarios.arachne.repository.entity.web.*;

import java.util.List;
import java.util.Set;

public interface EpisodeMapper {
	List<EpisodeEntity> selectEpisodesByWorkId(long workId);
	int insertEpisodes(Set<EpisodeCreationRequest> episodes);
	int updateEpisodes(Set<EpisodePostRequest> episodes);
	int updateEpisodeTitle(EpisodeTitleUpdateRequest episode);
	int updateEpisodeOrder(EpisodeOrderUpdateRequest episode);
	int removeEpisodes(Set<EpisodeRemovalRequest> episodes);
	int deleteEpisode(long id);
}
