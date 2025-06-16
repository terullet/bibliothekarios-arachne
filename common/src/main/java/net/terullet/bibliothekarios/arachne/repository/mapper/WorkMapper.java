package net.terullet.bibliothekarios.arachne.repository.mapper;

import net.terullet.bibliothekarios.arachne.repository.entity.WorkEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.WorkTitleUpdateRequest;

import java.util.List;

public interface WorkMapper {
	List<WorkEntity> selectWorksByUniverseId(long universeId);
	WorkEntity selectWorkById(long id);
	int updateWorkTitle(WorkTitleUpdateRequest work);
	int deleteWork(long id);
}
