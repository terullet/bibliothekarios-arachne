package net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.repository.Site;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouAllAgesGenre;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouWorkType;

import java.time.LocalDateTime;

public final class NarouAllAgesWorkEntity extends NarouWorkEntity {
	private final NarouAllAgesGenre genre;

	public NarouAllAgesWorkEntity(long id, String ncode, long narouId, NarouWorkType workType, NarouAllAgesGenre genre, String summary, LocalDateTime registeredAt) {
		super(id, Site.NAROU_ALL_AGES, ncode, narouId, workType, summary, registeredAt);
		this.genre = genre;
	}

	public NarouAllAgesGenre getGenre() {
		return this.genre;
	}
}
