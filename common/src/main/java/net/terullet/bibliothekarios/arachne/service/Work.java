package net.terullet.bibliothekarios.arachne.service;

import net.terullet.bibliothekarios.arachne.repository.Site;
import net.terullet.bibliothekarios.arachne.repository.entity.WorkEntity;

import java.time.LocalDateTime;
import java.util.List;

public record Work(
		long id,
		Site site,
		WorkTitleUpdate currentTitle,
		UniverseOverview universe,
		List<WorkTitleUpdate> titleUpdates,
		List<ContributionInWork> contributions,
		String summary,
		LocalDateTime registeredAt
) {

	public static Work fromEntity(WorkEntity workEntity) {
		List<WorkTitleUpdate> titleUpdates = workEntity.getTitleUpdates().stream().map(WorkTitleUpdate::fromEntity).toList();
		return new Work(
			workEntity.getId(),
			workEntity.getSite(),
			titleUpdates.getFirst(),
			UniverseOverview.fromEntity(workEntity.getUniverse()),
			titleUpdates.subList(1, titleUpdates.size()),
			workEntity.getContributions().stream()
				.map(ContributionInWork::fromEntity)
				.toList(),
			workEntity.getSummary(),
			workEntity.getRegisteredAt()
		);
	}
}
