package net.terullet.bibliothekarios.arachne.repository.mapper.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu.NarouWorkCreationRequest;
import net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu.NarouWorkEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu.NarouWorkUpdateRequest;

import java.util.List;

public interface NarouWorkMapper {
	List<NarouWorkEntity> selectAllNarouWorks();
	int insertNarouWork(NarouWorkCreationRequest work);
	int updateNarouWork(NarouWorkUpdateRequest work);
}
