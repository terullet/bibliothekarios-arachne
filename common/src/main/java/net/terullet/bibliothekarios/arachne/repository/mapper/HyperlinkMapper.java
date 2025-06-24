package net.terullet.bibliothekarios.arachne.repository.mapper;

import net.terullet.bibliothekarios.arachne.repository.entity.HyperlinkContributorBindRequestEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.HyperlinkCreationRequestEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.HyperlinkEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.HyperlinkWorkBindRequestEntity;

import java.util.List;
import java.util.Set;

public interface HyperlinkMapper {
	List<HyperlinkEntity> selectHyperlinksByWorkId(long workId);
	List<HyperlinkEntity> selectHyperlinksByContributorId(long contributorId);
	int insertHyperlinks(Set<HyperlinkCreationRequestEntity> hyperlinks);
	int bindHyperlinkToWork(HyperlinkWorkBindRequestEntity hyperlinkWorkBindRequest);
	int bindHyperlinkToContributor(HyperlinkContributorBindRequestEntity hyperlinkContributorBindRequest);
	int updateHyperlink(HyperlinkEntity hyperlink);
	int deleteHyperlink(long id);
}
