package net.terullet.bibliothekarios.arachne.service;

import net.terullet.bibliothekarios.arachne.repository.entity.UniverseOverviewEntity;

public record UniverseOverview(long id, String title) {

	public static UniverseOverview fromEntity(UniverseOverviewEntity universeOverview) {
		return new UniverseOverview(universeOverview.getId(), universeOverview.getTitle());
	}
}