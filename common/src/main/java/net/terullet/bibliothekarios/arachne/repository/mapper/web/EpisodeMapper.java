package net.terullet.bibliothekarios.arachne.repository.mapper.web;

import net.terullet.bibliothekarios.arachne.repository.entity.web.EpisodeEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.web.EpisodeOrderUpdateRequest;
import net.terullet.bibliothekarios.arachne.repository.entity.web.EpisodeTitleUpdateRequest;

import java.util.List;

public interface EpisodeMapper {
	List<EpisodeEntity> selectEpisodesByWorkId(long workId);
	int updateEpisodeTitle(EpisodeTitleUpdateRequest episode);
	int updateEpisodeOrder(EpisodeOrderUpdateRequest episode);
	int deleteEpisode(long id);
}
