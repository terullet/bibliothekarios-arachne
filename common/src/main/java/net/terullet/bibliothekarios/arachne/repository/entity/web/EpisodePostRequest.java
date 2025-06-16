package net.terullet.bibliothekarios.arachne.repository.entity.web;

import java.time.ZonedDateTime;

public class EpisodePostRequest {
	private Long postId;
	private final long episodeId;
	private final ZonedDateTime postedAt;

	public EpisodePostRequest(long episodeId, ZonedDateTime postedAt) {
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
