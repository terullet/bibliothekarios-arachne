package net.terullet.bibliothekarios.arachne.web.com.syosetu;

import java.util.List;

public interface NarouEpisodeMapper {
	List<NarouEpisode> getEpisodesByWorkId(long workId);
	int updateEpisode(NarouEpisode episode);
	int deleteEpisode(long id);
}
