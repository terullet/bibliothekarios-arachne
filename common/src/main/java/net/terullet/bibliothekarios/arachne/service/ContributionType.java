package net.terullet.bibliothekarios.arachne.service;

import net.terullet.bibliothekarios.arachne.repository.entity.ContributionTypeEntity;

public record ContributionType(int typeId, String typeName) {
	public static ContributionType fromEntity(ContributionTypeEntity contributionType) {
		return new ContributionType(
				contributionType.getTypeId(),
				contributionType.getTypeName()
		);
	}
}
