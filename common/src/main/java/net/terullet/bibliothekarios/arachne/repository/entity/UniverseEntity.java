package net.terullet.bibliothekarios.arachne.repository.entity;

import java.util.Collections;
import java.util.List;

public final class UniverseEntity {
	private final long id;
	private final String title;
	private final String summary;
	private final List<WorkOverviewEntity> works;

	public UniverseEntity(long id, String title, String summary, List<WorkOverviewEntity> works) {
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.works = Collections.unmodifiableList(works);
	}

	public long getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getSummary() {
		return this.summary;
	}

	public List<WorkOverviewEntity> getWorks() {
		return this.works;
	}
}
