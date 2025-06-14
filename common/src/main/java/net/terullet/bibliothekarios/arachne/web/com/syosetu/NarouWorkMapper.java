package net.terullet.bibliothekarios.arachne.web.com.syosetu;

import java.util.List;

public interface NarouWorkMapper {
	List<NarouWork> selectAllWorks();
	List<NarouWork> selectNarouWorksBySeriesId(long seriesId);
	int updateWork(NarouWork work);
	int deleteWork(long id);
}
