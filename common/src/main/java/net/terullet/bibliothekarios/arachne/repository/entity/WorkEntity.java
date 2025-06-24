package net.terullet.bibliothekarios.arachne.repository.entity;

import net.terullet.bibliothekarios.arachne.repository.Site;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class WorkEntity {
	private final long id;
	private final Site site;
	private final UniverseOverviewEntity universe;
	private final List<WorkTitleUpdateEntity> titleUpdates;
	private final List<ContributionInWorkEntity> contributions;
	private final String summary;
	private final LocalDateTime registeredAt;

	protected WorkEntity(long id, Site site, UniverseOverviewEntity universe, List<WorkTitleUpdateEntity> titleUpdates, List<ContributionInWorkEntity> contributions, String summary, LocalDateTime registeredAt) {
		this.id = id;
		this.site = site;
		this.universe = universe;
		this.titleUpdates = Collections.unmodifiableList(titleUpdates);
		this.contributions = Collections.unmodifiableList(contributions);
		this.summary = summary;
		this.registeredAt = registeredAt;
	}

	public final long getId() {
		return this.id;
	}
	public final Site getSite() {
		return this.site;
	}
	public final UniverseOverviewEntity getUniverse() {
		return this.universe;
	}
	public final List<WorkTitleUpdateEntity> getTitleUpdates() {
		return this.titleUpdates;
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
		private UniverseOverviewEntity universe;
		private List<WorkTitleUpdateEntity> titleUpdates;
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
		public final UniverseOverviewEntity getUniverse() {
			return this.universe;
		}
		public final List<WorkTitleUpdateEntity> getTitleUpdates() {
			return this.titleUpdates;
		}
		public final void setTitleUpdates(List<WorkTitleUpdateEntity> titleUpdates) {
			this.titleUpdates = titleUpdates;
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
			return new WorkEntity(this.getId(), this.getSite(), this.getUniverse(), this.getTitleUpdates(), this.getContributions(), this.getSummary(), this.getRegisteredAt());
		}
	}
}
