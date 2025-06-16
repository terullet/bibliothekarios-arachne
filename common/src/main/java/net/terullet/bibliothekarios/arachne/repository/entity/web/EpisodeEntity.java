package net.terullet.bibliothekarios.arachne.repository.entity.web;

import java.time.ZonedDateTime;

public final class EpisodeEntity {
	private final long id;
	private final String title;
	private final ZonedDateTime firstPostedAt;
	private final ZonedDateTime lastUpdatedAt;
	private final boolean isDeleted;

	public EpisodeEntity(long id, String title, ZonedDateTime firstPostedAt, ZonedDateTime lastUpdatedAt, boolean isDeleted) {
		this.id = id;
		this.title = title;
		this.firstPostedAt = firstPostedAt;
		this.lastUpdatedAt = lastUpdatedAt;
		this.isDeleted = isDeleted;
	}

	public long getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public ZonedDateTime getFirstPostedAt() {
		return this.firstPostedAt;
	}

	public ZonedDateTime getLastUpdatedAt() {
		return this.lastUpdatedAt;
	}

	public boolean isDeleted() {
		return this.isDeleted;
	}
}
