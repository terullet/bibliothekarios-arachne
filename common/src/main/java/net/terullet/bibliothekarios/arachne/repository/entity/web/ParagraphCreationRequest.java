package net.terullet.bibliothekarios.arachne.repository.entity.web;

public final class ParagraphCreationRequest {
	private Long id;
	private final long episodeId;
	private final long orderNumber;
	private final long postId;
	private final String content;

	public ParagraphCreationRequest(long episodeId, long orderNumber, long postId, String content) {
		this.episodeId = episodeId;
		this.orderNumber = orderNumber;
		this.postId = postId;
		this.content = content;
	}

	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getEpisodeId() {
		return this.episodeId;
	}
	public long getOrderNumber() {
		return this.orderNumber;
	}
	public long getPostId() {
		return this.postId;
	}
	public String getContent() {
		return this.content;
	}
}
