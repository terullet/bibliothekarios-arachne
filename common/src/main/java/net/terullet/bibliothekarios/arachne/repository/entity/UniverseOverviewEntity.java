package net.terullet.bibliothekarios.arachne.repository.entity;

public final class UniverseOverviewEntity {
	private final long id;
	private final String title;

	public UniverseOverviewEntity(long id, String title) {
		this.id = id;
		this.title = title;
	}

	public long getId() {
		return this.id;
	}
	public String getTitle() {
		return this.title;
	}
}
