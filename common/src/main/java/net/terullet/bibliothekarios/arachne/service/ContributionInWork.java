package net.terullet.bibliothekarios.arachne.service;

import net.terullet.bibliothekarios.arachne.repository.entity.ContributionInWorkEntity;

import java.util.List;

public record ContributionInWork(long contributorId, String handleName, List<ContributionType> contributionTypes) {

	public static ContributionInWork fromEntity(ContributionInWorkEntity contributionInWork) {
		return new ContributionInWork(
				contributionInWork.getContributorId(),
				contributionInWork.getHandleName(),
				contributionInWork.getContributionTypes().stream().map(ContributionType::fromEntity).toList()
		);
	}
}