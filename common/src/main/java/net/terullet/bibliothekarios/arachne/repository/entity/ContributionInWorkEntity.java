package net.terullet.bibliothekarios.arachne.repository.entity;

import java.util.Collections;
import java.util.List;

public class ContributionInWorkEntity {
	private final long contributorId;
	private final String handleName;
	private final List<ContributionTypeEntity> contributionTypes;

	public ContributionInWorkEntity(long contributorId, String handleName, List<ContributionTypeEntity> contributionTypes) {
		this.contributorId = contributorId;
		this.handleName = handleName;
		this.contributionTypes = Collections.unmodifiableList(contributionTypes);
	}

	public long getContributorId() {
		return this.contributorId;
	}
	public String getHandleName() {
		return this.handleName;
	}
	public List<ContributionTypeEntity> getContributionTypes() {
		return this.contributionTypes;
	}
}
