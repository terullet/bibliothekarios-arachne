package net.terullet.bibliothekarios.arachne.repository.entity.web;

import java.time.ZonedDateTime;

public final class EpisodePostRequestEntity {
	private Long postId;
	private final long episodeId;
	private final ZonedDateTime postedAt;

	public EpisodePostRequestEntity(long episodeId, ZonedDateTime postedAt) {
		this.episodeId = episodeId;
		this.postedAt = postedAt;
	}

	public Long getPostId() {
		return this.postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public long getEpisodeId() {
		return this.episodeId;
	}
	public ZonedDateTime getPostedAt() {
		return this.postedAt;
	}
}
