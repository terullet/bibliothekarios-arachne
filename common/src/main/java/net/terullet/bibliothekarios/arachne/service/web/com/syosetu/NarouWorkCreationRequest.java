package net.terullet.bibliothekarios.arachne.service.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouGenre;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouWorkType;
import net.terullet.bibliothekarios.arachne.repository.Site;
import net.terullet.bibliothekarios.arachne.repository.entity.WorkCreationRequestEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu.NarouWorkCreationRequestEntity;

import java.time.ZonedDateTime;

public record NarouWorkCreationRequest(
		String ncode,
		long narouId,
		boolean isR18,
		NarouWorkType workType,
		NarouGenre genre,
		String title,
		String summary,
		ZonedDateTime firstPostedAt
) {
	public WorkCreationRequestEntity toWorkCreationRequestEntity() {
		return new WorkCreationRequestEntity(this.isR18 ? Site.NAROU_R18 : Site.NAROU_ALL_AGES, this.summary);
	}
	public NarouWorkCreationRequestEntity toNarouWorkCreationRequestEntity(long workId) {
		return new NarouWorkCreationRequestEntity(workId, this.ncode, this.narouId, this.workType, this.genre, this.firstPostedAt);
	}
}
