package net.terullet.bibliothekarios.arachne.repository.mapper;

import net.terullet.bibliothekarios.arachne.repository.entity.ContributionEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.ContributorEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.WorkEntity;

import java.util.List;

public interface ContributionMapper {
	List<ContributorEntity> selectContributorsByWorkId(long workId);
	List<WorkEntity> selectWorksByContributorId(long contributorId);
	int insertContribution(ContributionEntity contribution);
	int deleteContribution(ContributionEntity contribution);
}
