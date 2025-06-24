package net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouGenre;
import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouWorkType;

import java.time.LocalDateTime;

public final class NarouWorkCreationRequestEntity {
	private Long id;
	private final String ncode;
	private final long narouId;
	private final NarouWorkType workType;
	private final NarouGenre genre;
	private final String summary;
	private LocalDateTime registeredAt;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setRegisteredAt(LocalDateTime registeredAt) {
		this.registeredAt = registeredAt;
	}

	public NarouWorkCreationRequestEntity(String ncode, long narouId, NarouWorkType workType, NarouGenre genre, String summary) {
		this.ncode = ncode;
		this.narouId = narouId;
		this.workType = workType;
		this.genre = genre;
		this.summary = summary;
	}
}
