package net.terullet.bibliothekarios.arachne.repository.entity.web;

import java.time.ZonedDateTime;

public final class EpisodeEntity {
	private final long id;
	private final long workId;
	private final String title;
	private final long orderNumber;
	private final ZonedDateTime firstPostedAt;

	public EpisodeEntity(long id, long workId, String title, long orderNumber, ZonedDateTime firstPostedAt) {
		this.id = id;
		this.workId = workId;
		this.title = title;
		this.orderNumber = orderNumber;
		this.firstPostedAt = firstPostedAt;
	}

	public long getId() {
		return this.id;
	}

	public long getWorkId() {
		return this.workId;
	}

	public String getTitle() {
		return this.title;
	}

	public long getOrderNumber() {
		return this.orderNumber;
	}

	public ZonedDateTime getFirstPostedAt() {
		return this.firstPostedAt;
	}
}
