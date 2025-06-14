package net.terullet.bibliothekarios.arachne.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.Contributor;
import net.terullet.bibliothekarios.arachne.Series;
import net.terullet.bibliothekarios.arachne.web.com.syosetu.api.NarouWorkResponseMetadata;

import java.time.ZonedDateTime;
import java.util.Set;

public class NarouWorkCreationRequest {
	private Long id;
	private Series series;
	private final String title;
	private final String ncode;
	private Long narouId;
	private final NarouWorkType workType;
	private final String summary;
	private final Set<String> keywords;
	private Contributor writer;
	private final ZonedDateTime firstPostedAt;
	private final ZonedDateTime lastPostedAt;
	private final ZonedDateTime lastUpdatedAt;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Series getSeries() {
		return this.series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public String getTitle() {
		return this.title;
	}

	public String getNcode() {
		return this.ncode;
	}

	public long getNarouId() {
		return this.narouId;
	}

	public void setNarouId(long narouId) {
		this.narouId = narouId;
	}

	public NarouWorkType getWorkType() {
		return this.workType;
	}

	public String getSummary() {
		return this.summary;
	}

	public Set<String> getKeywords() {
		return this.keywords;
	}

	public Contributor getWriter() {
		return this.writer;
	}

	public void setWriter(Contributor writer) {
		this.writer = writer;
	}

	public ZonedDateTime getLastPostedAt() {
		return this.lastPostedAt;
	}

	public ZonedDateTime getLastUpdatedAt() {
		return this.lastUpdatedAt;
	}

	public ZonedDateTime getFirstPostedAt() {
		return this.firstPostedAt;
	}

	public NarouWorkCreationRequest(NarouWorkResponseMetadata workMetadata) {
		this.ncode = workMetadata.getNcode();
		this.title = workMetadata.getTitle();
		this.workType = workMetadata.getWorkType();
		this.keywords = workMetadata.getKeywords();
		this.summary = workMetadata.getSummary();
		this.firstPostedAt = workMetadata.getFirstPostedAt();
		this.lastPostedAt = workMetadata.getLastPostedAt();
		this.lastUpdatedAt = workMetadata.getLastUpdatedAt();
	}
}
