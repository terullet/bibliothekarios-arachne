package net.terullet.bibliothekarios.arachne.repository.mapper;

import net.terullet.bibliothekarios.arachne.repository.entity.UniverseCreationRequestEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.UniverseEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.UniverseOverviewEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.UniverseUpdateRequestEntity;

public interface UniverseMapper {
	UniverseEntity selectUniverseById(long id);
	UniverseOverviewEntity selectUniverseByWorkId(long workId);
	int insertUniverse(UniverseCreationRequestEntity universe);
	int updateUniverse(UniverseUpdateRequestEntity universe);
	int deleteUniverse(long id);
}
