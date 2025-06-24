package net.terullet.bibliothekarios.arachne.repository.mapper.web;

import net.terullet.bibliothekarios.arachne.repository.entity.web.*;

import java.util.List;
import java.util.Set;

public interface EpisodeMapper {
	List<EpisodeEntity> selectEpisodesByWorkId(long workId);
	List<EpisodeHistoryEntity> selectEpisodeHistoriesByEpisodeId(long episodeId);
	int insertEpisodes(Set<EpisodeCreationRequestEntity> episodes);
	int updateEpisodes(Set<EpisodePostRequestEntity> episodes);
	int updateEpisodeTitle(EpisodeTitleUpdateRequestEntity episode);
	int updateEpisodeOrder(EpisodeOrderUpdateRequestEntity episode);
	int removeEpisodes(Set<EpisodeRemovalRequestEntity> episodes);
	int deleteEpisode(long id);
}
