package net.terullet.bibliothekarios.arachne.repository.mapper;

import net.terullet.bibliothekarios.arachne.repository.entity.WorkEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.WorkOverviewEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.WorkTitleUpdateRequestEntity;

import java.util.List;

public interface WorkMapper {
	List<WorkOverviewEntity> selectAllWorks();
	List<WorkOverviewEntity> selectWorksByUniverseId(long universeId);
	WorkEntity.Factory selectWorkById(long id);
	int updateWorkTitle(WorkTitleUpdateRequestEntity work);
	int deleteWork(long id);
}
