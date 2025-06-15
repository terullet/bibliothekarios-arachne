package net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.domain.Site;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouGenre;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouWorkType;
import net.terullet.bibliothekarios.arachne.repository.entity.WorkEntity;

import java.time.LocalDateTime;

public final class NarouWorkEntity implements WorkEntity {
	private final long id;
	private final Site site;
	private final String ncode;
	private final long narouId;
	private final NarouWorkType workType;
	private final NarouGenre genre;
	private final String summary;
	private final LocalDateTime registeredAt;

	public NarouWorkEntity(long id, Site site, String ncode, long narouId, NarouWorkType workType, NarouGenre genre, String summary, LocalDateTime registeredAt) {
		this.id = id;
		this.site = site;
		this.ncode = ncode;
		this.narouId = narouId;
		this.workType = workType;
		this.genre = genre;
		this.summary = summary;
		this.registeredAt = registeredAt;
	}

	public long getId() {
		return this.id;
	}
	public Site getSite() {
		return this.site;
	}
	public String getNcode() {
		return this.ncode;
	}
	public long getNarouId() {
		return this.narouId;
	}
	public NarouWorkType getWorkType() {
		return this.workType;
	}
	public NarouGenre getGenre() {
		return this.genre;
	}
	public String getSummary() {
		return this.summary;
	}
	public LocalDateTime getRegisteredAt() {
		return this.registeredAt;
	}
}
