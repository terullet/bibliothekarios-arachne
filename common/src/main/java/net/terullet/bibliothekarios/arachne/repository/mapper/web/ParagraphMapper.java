package net.terullet.bibliothekarios.arachne.repository.mapper.web;

import net.terullet.bibliothekarios.arachne.repository.entity.web.ParagraphEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.web.ParagraphOrderUpdateRequest;

import java.util.List;

public interface ParagraphMapper {
	List<ParagraphEntity> getParagraphsByEpisodeId(long id);
	int updateParagraphOrder(ParagraphOrderUpdateRequest paragraph);
	int deleteParagraph(long id);
}
