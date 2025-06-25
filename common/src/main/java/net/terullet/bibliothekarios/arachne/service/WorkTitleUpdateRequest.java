package net.terullet.bibliothekarios.arachne.service;

import net.terullet.bibliothekarios.arachne.repository.entity.WorkTitleUpdateRequestEntity;

import java.time.ZonedDateTime;

public record WorkTitleUpdateRequest(long id, String newTitle, ZonedDateTime updatedAt) {
	public WorkTitleUpdateRequestEntity toEntity() {
		return new WorkTitleUpdateRequestEntity(this.id, this.newTitle, this.updatedAt);
	}
}
