package net.terullet.bibliothekarios.arachne.repository.entity;

import java.time.ZonedDateTime;

public final class WorkTitleUpdateEntity {
	private final long workId;
	private final String title;
	private final ZonedDateTime updatedAt;

	public WorkTitleUpdateEntity(long workId, String title, ZonedDateTime updatedAt) {
		this.workId = workId;
		this.title = title;
		this.updatedAt = updatedAt;
	}

	public long getWorkId() {
		return this.workId;
	}
	public String getTitle() {
		return this.title;
	}
	public ZonedDateTime getUpdatedAt() {
		return this.updatedAt;
	}
}
