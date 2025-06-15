package net.terullet.bibliothekarios.arachne;

import java.time.ZonedDateTime;
import java.util.List;

public interface WorkMapper {
	List<Work> getWorksByUniverseId(long universeId);
	List<? extends Work> getWorksBySiteId(int siteId);
	Work getWorkById(long id);
	int deleteWork(long id);
	int updateWorkTitle(long id, String title, ZonedDateTime updatedAt);
	int updateWorkSummary(long id, String summary);
}
