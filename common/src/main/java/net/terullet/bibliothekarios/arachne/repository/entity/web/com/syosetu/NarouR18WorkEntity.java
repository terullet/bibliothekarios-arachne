package net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.repository.Site;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouR18Genre;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouWorkType;

import java.time.LocalDateTime;

public final class NarouR18WorkEntity extends NarouWorkEntity {
	private final NarouR18Genre genre;

	public NarouR18WorkEntity(long id, String ncode, long narouId, NarouWorkType workType, NarouR18Genre genre, String summary, LocalDateTime registeredAt) {
		super(id, Site.NAROU_R18, ncode, narouId, workType, summary, registeredAt);
		this.genre = genre;
	}

	public NarouR18Genre getGenre() {
		return this.genre;
	}
}
