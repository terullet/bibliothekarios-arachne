package net.terullet.bibliothekarios.arachne.repository.entity;

import net.terullet.bibliothekarios.arachne.repository.Site;

import java.time.LocalDateTime;

public class WorkCreationRequestEntity {
	private Long id;
	private final Site site;
	private final String summary;
	private LocalDateTime registeredAt;

	public WorkCreationRequestEntity(Site site, String summary) {
		this.site = site;
		this.summary = summary;
	}

	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Site getSite() {
		return this.site;
	}
	public String getSummary() {
		return this.summary;
	}
	public LocalDateTime getRegisteredAt() {
		return this.registeredAt;
	}
	public void setRegisteredAt(LocalDateTime registeredAt) {
		this.registeredAt = registeredAt;
	}
}
