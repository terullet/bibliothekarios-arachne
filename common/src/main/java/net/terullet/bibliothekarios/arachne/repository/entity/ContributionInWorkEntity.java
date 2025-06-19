package net.terullet.bibliothekarios.arachne.repository.entity;

public class ContributionInWorkEntity {
	private final long contributorId;
	private final String handleName;
	private final int contributionTypeId;
	private final String typeName;

	public ContributionInWorkEntity(long contributorId, String handleName, int contributionTypeId, String typeName) {
		this.contributorId = contributorId;
		this.handleName = handleName;
		this.contributionTypeId = contributionTypeId;
		this.typeName = typeName;
	}

	public long getContributorId() {
		return this.contributorId;
	}
	public String getHandleName() {
		return this.handleName;
	}
	public int getContributionTypeId() {
		return this.contributionTypeId;
	}
	public String getTypeName() {
		return this.typeName;
	}
}
