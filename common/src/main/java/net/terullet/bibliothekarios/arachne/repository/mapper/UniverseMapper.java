package net.terullet.bibliothekarios.arachne.repository.mapper;

import net.terullet.bibliothekarios.arachne.repository.entity.UniverseCreationRequest;
import net.terullet.bibliothekarios.arachne.repository.entity.UniverseEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.UniverseOverviewEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.UniverseUpdateRequest;

public interface UniverseMapper {
	UniverseEntity selectUniverseById(long id);
	UniverseOverviewEntity selectUniverseByWorkId(long workId);
	int insertUniverse(UniverseCreationRequest universe);
	int updateUniverse(UniverseUpdateRequest universe);
	int deleteUniverse(long id);
}
