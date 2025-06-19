package net.terullet.bibliothekarios.arachne.repository.entity.web;

import java.time.ZonedDateTime;

public final class EpisodeUpdateEntity implements EpisodeHistoryEntity {
	private final long postId;
	private final long episodeId;
	private final ZonedDateTime postedAt;

	public EpisodeUpdateEntity(long postId, long episodeId, ZonedDateTime postedAt) {
		this.postId = postId;
		this.episodeId = episodeId;
		this.postedAt = postedAt;
	}

	public long getPostId() {
		return this.postId;
	}
	public long getEpisodeId() {
		return this.episodeId;
	}
	public ZonedDateTime getPostedAt() {
		return this.postedAt;
	}
}
