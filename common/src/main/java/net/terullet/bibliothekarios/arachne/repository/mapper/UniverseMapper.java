package net.terullet.bibliothekarios.arachne.repository.mapper;

import net.terullet.bibliothekarios.arachne.repository.entity.UniverseCreationRequest;
import net.terullet.bibliothekarios.arachne.repository.entity.UniverseEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.UniverseUpdateRequest;

public interface UniverseMapper {
	UniverseEntity getUniverseById(long id);
	int insertUniverse(UniverseCreationRequest workUniverse);
	int updateUniverse(UniverseUpdateRequest universe);
	int deleteUniverse(long id);
}
