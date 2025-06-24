package net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouGenre;

public final class NarouWorkUpdateRequestEntity {
	private final long id;
	private final NarouGenre genre;
	private final String summary;

	public NarouWorkUpdateRequestEntity(long id, NarouGenre genre, String summary) {
		this.id = id;
		this.genre = genre;
		this.summary = summary;
	}

	public long getId() {
		return this.id;
	}
	public NarouGenre getGenre() {
		return this.genre;
	}
	public String getSummary() {
		return this.summary;
	}
}
