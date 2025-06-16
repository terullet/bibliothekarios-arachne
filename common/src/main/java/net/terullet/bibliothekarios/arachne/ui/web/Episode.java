package net.terullet.bibliothekarios.arachne.ui.web;

import java.time.ZonedDateTime;

public interface Episode {
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
