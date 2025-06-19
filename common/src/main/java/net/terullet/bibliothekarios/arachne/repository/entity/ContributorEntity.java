package net.terullet.bibliothekarios.arachne.repository.entity;

public final class ContributorEntity {
	private final long id;
	private final String handleName;

	public ContributorEntity(long id, String handleName) {
		this.id = id;
		this.handleName = handleName;
	}

	public long getId() {
		return this.id;
	}
	public String getHandleName() {
		return this.handleName;
	}
}
