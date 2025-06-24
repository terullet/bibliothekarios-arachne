package net.terullet.bibliothekarios.arachne.repository.entity;

public final class UniverseCreationRequestEntity {
	private Long id;
	private final String title;
	private final String summary;

	public UniverseCreationRequestEntity(String title, String summary) {
		this.title = title;
		this.summary = summary;
	}

	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return this.title;
	}
	public String getSummary() {
		return this.summary;
	}
}
