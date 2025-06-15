package net.terullet.bibliothekarios.arachne.repository.entity.web;

public final class EpisodeTitleUpdateRequest {
	private final long id;
	private final String newTitle;

	public EpisodeTitleUpdateRequest(long id, String newTitle) {
		this.id = id;
		this.newTitle = newTitle;
	}

	public long getId() {
		return this.id;
	}
	public String getNewTitle() {
		return this.newTitle;
	}
}
