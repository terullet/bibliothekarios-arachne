package net.terullet.bibliothekarios.arachne.repository.entity.web;

public final class ParagraphContentDeleteEventEntity {
	private final long paragraphId;
	private final long postId;
	private final long episodeId;

	public ParagraphContentDeleteEventEntity(long paragraphId, long postId, long episodeId) {
		this.paragraphId = paragraphId;
		this.postId = postId;
		this.episodeId = episodeId;
	}

	public long getParagraphId() {
		return this.paragraphId;
	}
	public long getPostId() {
		return this.postId;
	}
	public long getEpisodeId() {
		return this.episodeId;
	}
}
