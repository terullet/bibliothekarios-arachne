package net.terullet.bibliothekarios.arachne.repository.entity.web;

public final class ParagraphEntity {
	private final long id;
	private final long episodeId;
	private final long orderNumber;

	public ParagraphEntity(long id, long episodeId, long orderNumber) {
		this.id = id;
		this.episodeId = episodeId;
		this.orderNumber = orderNumber;
	}

	public long getId() {
		return this.id;
	}
	public long getEpisodeId() {
		return this.episodeId;
	}
	public long getOrderNumber() {
		return this.orderNumber;
	}
}
