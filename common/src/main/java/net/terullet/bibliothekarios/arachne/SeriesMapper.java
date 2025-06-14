package net.terullet.bibliothekarios.arachne;

public interface SeriesMapper {
	Series getWorkById(long id);
	int updateWork(Series series);
	int deleteWork(long id);
}
