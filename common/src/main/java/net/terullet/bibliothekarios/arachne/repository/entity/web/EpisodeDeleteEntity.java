package net.terullet.bibliothekarios.arachne.repository.entity.web;

import java.time.ZonedDateTime;

public final class EpisodeDeleteEntity implements EpisodeHistoryEntity {
	private final long episodeId;
	private final ZonedDateTime deletedBefore;

	public EpisodeDeleteEntity(long episodeId, ZonedDateTime deletedBefore) {
		this.episodeId = episodeId;
		this.deletedBefore = deletedBefore;
	}

	public long getEpisodeId() {
		return this.episodeId;
	}
	public ZonedDateTime getDeletedBefore() {
		return this.deletedBefore;
	}
}
