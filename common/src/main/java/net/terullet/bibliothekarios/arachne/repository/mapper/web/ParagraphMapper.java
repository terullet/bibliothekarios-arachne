package net.terullet.bibliothekarios.arachne.repository.mapper.web;

import net.terullet.bibliothekarios.arachne.repository.entity.web.ParagraphEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.web.ParagraphOrderUpdateRequestEntity;

import java.util.List;

public interface ParagraphMapper {
	List<ParagraphEntity> selectParagraphsByEpisodeId(long id);
	int updateParagraphOrder(ParagraphOrderUpdateRequestEntity paragraph);
	int deleteParagraph(long id);
}
