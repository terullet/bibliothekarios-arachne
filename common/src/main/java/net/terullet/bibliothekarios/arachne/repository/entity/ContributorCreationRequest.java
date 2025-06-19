package net.terullet.bibliothekarios.arachne.repository.entity;

public final class ContributorCreationRequest {
	private Long id;
	private final String handleName;

	public ContributorCreationRequest(String handleName) {
		this.handleName = handleName;
	}

	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHandleName() {
		return this.handleName;
	}
}
