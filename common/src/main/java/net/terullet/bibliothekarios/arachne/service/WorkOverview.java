package net.terullet.bibliothekarios.arachne.service;

import net.terullet.bibliothekarios.arachne.repository.entity.WorkOverviewEntity;

public record WorkOverview(long id, String title) {

	public static WorkOverview fromEntity(WorkOverviewEntity workOverview) {
		return new WorkOverview(workOverview.getId(), workOverview.getTitle());
	}
}
