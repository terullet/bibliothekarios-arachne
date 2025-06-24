package net.terullet.bibliothekarios.arachne.repository.mapper.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu.NarouWorkCreationRequestEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu.NarouWorkEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu.NarouWorkUpdateRequestEntity;

import java.util.List;

public interface NarouWorkMapper {
	List<NarouWorkEntity> selectAllNarouWorks();
	int insertNarouWork(NarouWorkCreationRequestEntity work);
	int updateNarouWork(NarouWorkUpdateRequestEntity work);
}
