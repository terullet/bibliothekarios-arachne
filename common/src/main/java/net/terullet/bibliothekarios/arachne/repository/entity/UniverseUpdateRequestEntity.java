package net.terullet.bibliothekarios.arachne.repository.entity;

public final class UniverseUpdateRequestEntity {
	private final long id;
	private final String newTitle;
	private final String newSummary;

	public UniverseUpdateRequestEntity(long id, String newTitle, String newSummary) {
		this.id = id;
		this.newTitle = newTitle;
		this.newSummary = newSummary;
	}

	public long getId() {
		return this.id;
	}
	public String getNewTitle() {
		return this.newTitle;
	}
	public String getNewSummary() {
		return this.newSummary;
	}
}
