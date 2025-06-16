package net.terullet.bibliothekarios.arachne.repository.mapper.web;

import net.terullet.bibliothekarios.arachne.repository.entity.web.ParagraphContentDeleteEventEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.web.ParagraphContentEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.web.ParagraphContentUpdateEventEntity;

import java.util.List;

public interface ParagraphContentMapper {
	List<ParagraphContentEntity> selectCurrentlyAliveParagraphContentsByEpisodeId(long episodeId);
	int updateParagraphContent(ParagraphContentUpdateEventEntity paragraphContent);
	int deleteParagraphContent(ParagraphContentDeleteEventEntity paragraphContent);
}
