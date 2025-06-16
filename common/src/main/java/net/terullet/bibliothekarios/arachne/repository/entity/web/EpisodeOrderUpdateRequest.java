package net.terullet.bibliothekarios.arachne.repository.entity.web;

public final class EpisodeOrderUpdateRequest {
	private final long id;
	private final long newOrderNumber;

	public EpisodeOrderUpdateRequest(long id, long newOrderNumber) {
		this.id = id;
		this.newOrderNumber = newOrderNumber;
	}

	public long getId() {
		return this.id;
	}
	public long getNewOrderNumber() {
		return this.newOrderNumber;
	}
}
