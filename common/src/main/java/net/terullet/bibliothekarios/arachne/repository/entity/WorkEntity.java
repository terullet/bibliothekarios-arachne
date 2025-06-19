package net.terullet.bibliothekarios.arachne.repository.entity;

import net.terullet.bibliothekarios.arachne.repository.Site;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class WorkEntity {
	private final long id;
	private final Site site;
	private final String title;
	private final UniverseOverviewEntity universe;
	private final List<ContributionInWorkEntity> contributions;
	private final String summary;
	private final LocalDateTime registeredAt;

	protected WorkEntity(long id, Site site, String title, UniverseOverviewEntity universe, List<ContributionInWorkEntity> contributions, String summary, LocalDateTime registeredAt) {
		this.id = id;
		this.site = site;
		this.title = title;
		this.universe = universe; this.contributions = Collections.unmodifiableList(contributions);
		this.summary = summary;
		this.registeredAt = registeredAt;
	}

	public final long getId() {
		return this.id;
	}
	public final Site getSite() {
		return this.site;
	}
	public final String getTitle() {
		return this.title;
	}
	public final UniverseOverviewEntity getUniverse() {
		return this.universe;
	}
	public final List<ContributionInWorkEntity> getContributions() {
		return this.contributions;
	}
	public final String getSummary() {
		return this.summary;
	}
	public final LocalDateTime getRegisteredAt() {
		return this.registeredAt;
	}

	public static class Factory {
		private Long id;
		private Site site;
		private String title;
		private UniverseOverviewEntity universe;
		private List<ContributionInWorkEntity> contributions;
		private String summary;
		private LocalDateTime registeredAt;

		public final Long getId() {
			return this.id;
		}
		public final void setId(Long id) {
			this.id = id;
		}
		public final Site getSite() {
			return this.site;
		}
		public void setSite(Site site) {
			this.site = site;
		}
		public final String getTitle() {
			return this.title;
		}
		public final void setTitle(String title) {
			this.title = title;
		}
		public final UniverseOverviewEntity getUniverse() {
			return this.universe;
		}
		public final void setUniverse(UniverseOverviewEntity universe) {
			this.universe = universe;
		}
		public final List<ContributionInWorkEntity> getContributions() {
			return this.contributions;
		}
		public final void setContributions(List<ContributionInWorkEntity> contributions) {
			this.contributions = contributions;
		}
		public final String getSummary() {
			return this.summary;
		}
		public final void setSummary(String summary) {
			this.summary = summary;
		}
		public final LocalDateTime getRegisteredAt() {
			return this.registeredAt;
		}
		public final void setRegisteredAt(LocalDateTime registeredAt) {
			this.registeredAt = registeredAt;
		}
		public WorkEntity build() {
			return new WorkEntity(this.getId(), this.getSite(), this.getTitle(), this.getUniverse(), this.getContributions(), this.getSummary(), this.getRegisteredAt());
		}
	}
}
