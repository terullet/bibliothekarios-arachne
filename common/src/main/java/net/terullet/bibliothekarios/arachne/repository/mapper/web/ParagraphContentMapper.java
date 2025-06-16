package net.terullet.bibliothekarios.arachne.repository.mapper.web;

import net.terullet.bibliothekarios.arachne.repository.entity.web.ParagraphContentDeleteEventEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.web.ParagraphContentEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.web.ParagraphContentUpdateEventEntity;

import java.util.List;
import java.util.Set;

public interface ParagraphContentMapper {
	List<ParagraphContentEntity> selectCurrentlyAliveParagraphContentsByEpisodeId(long episodeId);
	int updateParagraphContents(Set<ParagraphContentUpdateEventEntity> paragraphContent);
	int deleteParagraphContents(Set<ParagraphContentDeleteEventEntity> paragraphContent);
}
