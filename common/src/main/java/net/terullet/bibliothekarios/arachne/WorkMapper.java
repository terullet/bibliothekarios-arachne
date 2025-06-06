package net.terullet.bibliothekarios.arachne;

import java.util.List;

public interface WorkMapper {
	List<Work> getWorksByUniverseId(long universeId);
	Work getWorkById(long id);
	int deleteWork(long id);
}
