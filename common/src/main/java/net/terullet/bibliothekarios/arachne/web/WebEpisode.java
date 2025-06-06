package net.terullet.bibliothekarios.arachne.web;

import java.time.ZonedDateTime;

public interface WebEpisode {
	WebWork getWebWork();
	String getTitle();
	void setTitle(String title);
	int getEpisodeNumber();
	void setEpisodeNumber(int episodeNumber);
	ZonedDateTime getPostedAt();
	ZonedDateTime getLastUpdatedAt();
	void setLastUpdatedAt(ZonedDateTime lastUpdatedAt);
	ZonedDateTime getRemovedAt();
	void setRemovedAt(ZonedDateTime removedAt);
}
