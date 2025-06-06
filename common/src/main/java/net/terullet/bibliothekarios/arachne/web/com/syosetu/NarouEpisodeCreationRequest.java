package net.terullet.bibliothekarios.arachne.web.com.syosetu;

import java.time.ZonedDateTime;

public class NarouEpisodeCreationRequest {
	private Long id;
	private NarouWork work;
	private int episodeNumber;
	private String title;
	private ZonedDateTime postedAt;
	private ZonedDateTime lastUpdatedAt;
	private ZonedDateTime removedAt;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NarouWork getWork() {
		return this.work;
	}

	public void setWork(NarouWork work) {
		this.work = work;
	}

	public int getEpisodeNumber() {
		return this.episodeNumber;
	}

	public void setEpisodeNumber(int episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ZonedDateTime getPostedAt() {
		return this.postedAt;
	}

	public void setPostedAt(ZonedDateTime postedAt) {
		this.postedAt = postedAt;
	}

	public ZonedDateTime getLastUpdatedAt() {
		return this.lastUpdatedAt;
	}

	public void setLastUpdatedAt(ZonedDateTime lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public ZonedDateTime getRemovedAt() {
		return this.removedAt;
	}

	public void setRemovedAt(ZonedDateTime removedAt) {
		this.removedAt = removedAt;
	}
}
