package net.terullet.bibliothekarios.arachne.service;

import net.terullet.bibliothekarios.arachne.repository.entity.WorkTitleUpdateEntity;

import java.time.ZonedDateTime;

public record WorkTitleUpdate(String title, ZonedDateTime updatedAt) {
	public static WorkTitleUpdate fromEntity(WorkTitleUpdateEntity entity) {
		return new WorkTitleUpdate(entity.getTitle(), entity.getUpdatedAt());
	}
}
