package net.terullet.bibliothekarios.arachne.repository.mapper;

import net.terullet.bibliothekarios.arachne.repository.entity.ContributorCreationRequestEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.ContributorEntity;

import java.util.List;

public interface ContributorMapper {
	List<ContributorEntity> selectAllContributors();
	int insertContributor(ContributorCreationRequestEntity contributor);
	int deleteContributor(long id);
}
