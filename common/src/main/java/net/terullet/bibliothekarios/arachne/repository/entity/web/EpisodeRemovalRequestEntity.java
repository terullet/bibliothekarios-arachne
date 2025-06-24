package net.terullet.bibliothekarios.arachne.repository.entity.web;

import java.time.ZonedDateTime;

public final class EpisodeRemovalRequestEntity {
	private final long episodeId;
	private final ZonedDateTime removedAt;

	public EpisodeRemovalRequestEntity(long episodeId, ZonedDateTime removedAt) {
		this.episodeId = episodeId;
		this.removedAt = removedAt;
	}

	public long getEpisodeId() {
		return this.episodeId;
	}
	public ZonedDateTime getRemovedAt() {
		return this.removedAt;
	}
}
