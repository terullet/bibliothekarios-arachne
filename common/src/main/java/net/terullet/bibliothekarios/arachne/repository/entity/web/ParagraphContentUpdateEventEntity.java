package net.terullet.bibliothekarios.arachne.repository.entity.web;

public final class ParagraphContentUpdateEventEntity {
	private final long paragraphId;
	private final long postId;
	private final long episodeId;
	private final String content;

	public ParagraphContentUpdateEventEntity(long paragraphId, long postId, long episodeId, String content) {
		this.paragraphId = paragraphId;
		this.postId = postId;
		this.episodeId = episodeId;
		this.content = content;
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
	public String getContent() {
		return this.content;
	}
}
