package net.terullet.bibliothekarios.arachne.repository.entity;

import java.time.ZonedDateTime;

public final class WorkTitleUpdateRequestEntity {
	private final long id;
	private final String newTitle;
	private final ZonedDateTime updatedAt;

	public WorkTitleUpdateRequestEntity(long id, String newTitle, ZonedDateTime updatedAt) {
		this.id = id;
		this.newTitle = newTitle;
		this.updatedAt = updatedAt;
	}

	public long getId() {
		return this.id;
	}
	public String getNewTitle() {
		return this.newTitle;
	}
	public ZonedDateTime getUpdatedAt() {
		return this.updatedAt;
	}
}
