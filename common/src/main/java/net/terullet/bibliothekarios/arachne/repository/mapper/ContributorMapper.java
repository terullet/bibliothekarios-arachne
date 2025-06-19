package net.terullet.bibliothekarios.arachne.repository.mapper;

import net.terullet.bibliothekarios.arachne.repository.entity.ContributorCreationRequest;
import net.terullet.bibliothekarios.arachne.repository.entity.ContributorEntity;

import java.util.List;

public interface ContributorMapper {
	List<ContributorEntity> selectAllContributors();
	int insertContributor(ContributorCreationRequest contributor);
	int deleteContributor(long id);
}
