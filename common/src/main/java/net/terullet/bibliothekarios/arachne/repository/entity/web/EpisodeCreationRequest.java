package net.terullet.bibliothekarios.arachne.repository.entity.web;

import java.time.ZonedDateTime;

public final class EpisodeCreationRequest {
	private Long id;
	private Long postId;
	private final long workId;
	private final String title;
	private final int orderNumber;
	private final ZonedDateTime firstPostedAt;
	private final ZonedDateTime postedAt;

	public EpisodeCreationRequest(long workId, int orderNumber, String title, ZonedDateTime firstPostedAt, ZonedDateTime postedAt) {
		this.workId = workId;
		this.orderNumber = orderNumber;
		this.title = title;
		this.firstPostedAt = firstPostedAt;
		this.postedAt = postedAt;
	}

	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPostId() {
		return this.postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public long getWorkId() {
		return this.workId;
	}
	public int getOrderNumber() {
		return this.orderNumber;
	}
	public String getTitle() {
		return this.title;
	}
	public ZonedDateTime getFirstPostedAt() {
		return this.firstPostedAt;
	}
	public ZonedDateTime getPostedAt() {
		return this.postedAt;
	}
}
