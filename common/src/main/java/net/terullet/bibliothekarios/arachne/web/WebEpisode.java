package net.terullet.bibliothekarios.arachne.web;

import java.time.ZonedDateTime;
import java.util.concurrent.CompletionStage;

public interface WebEpisode {
	WebWork getWebWork();
	String getTitle();
	void setTitle(String title);
	ZonedDateTime getPostedAt();
	ZonedDateTime getUpdatedAt();
	void setUpdatedAt(ZonedDateTime updatedAt);
	CompletionStage<String> getContent();
}
