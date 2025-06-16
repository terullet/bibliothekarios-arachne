package net.terullet.bibliothekarios.arachne.repository.entity;

public final class UniverseEntity {
	private final long id;
	private final String title;
	private final String summary;

	public UniverseEntity(long id, String title, String summary) {
		this.id = id;
		this.title = title;
		this.summary = summary;
	}

	public long getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getSummary() {
		return this.summary;
	}
}
