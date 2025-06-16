package net.terullet.bibliothekarios.arachne.repository.mapper;

import net.terullet.bibliothekarios.arachne.repository.entity.HyperlinkContributorBindRequest;
import net.terullet.bibliothekarios.arachne.repository.entity.HyperlinkCreationRequest;
import net.terullet.bibliothekarios.arachne.repository.entity.HyperlinkEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.HyperlinkWorkBindRequest;

import java.util.List;
import java.util.Set;

public interface HyperlinkMapper {
	List<HyperlinkEntity> selectHyperlinksByWorkId(long workId);
	List<HyperlinkEntity> selectHyperlinksByContributorId(long contributorId);
	int insertHyperlinks(Set<HyperlinkCreationRequest> hyperlinks);
	int bindHyperlinkToWork(HyperlinkWorkBindRequest hyperlinkWorkBindRequest);
	int bindHyperlinkToContributor(HyperlinkContributorBindRequest hyperlinkContributorBindRequest);
	int updateHyperlink(HyperlinkEntity hyperlink);
	int deleteHyperlink(long id);
}
