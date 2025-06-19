package net.terullet.bibliothekarios.arachne.repository.entity;

public final class ContributionEntity {
	private final long workId;
	private final long contributorId;
	private final int contributionTypeId;

	public ContributionEntity(long workId, long contributorId, int contributionTypeId) {
		this.workId = workId;
		this.contributorId = contributorId;
		this.contributionTypeId = contributionTypeId;
	}

	public long getWorkId() {
		return this.workId;
	}
	public long getContributorId() {
		return this.contributorId;
	}
	public int getContributionTypeId() {
		return this.contributionTypeId;
	}
}
