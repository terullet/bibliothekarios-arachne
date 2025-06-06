package net.terullet.bibliothekarios.arachne;

public interface UniverseMapper {
	Universe getUniverseById(long id);
	int updateUniverse(Universe universe);
	int deleteUniverse(long id);
}
