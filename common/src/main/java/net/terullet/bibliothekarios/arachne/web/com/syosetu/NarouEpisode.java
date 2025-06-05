package net.terullet.bibliothekarios.arachne.web.com.syosetu;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public interface NarouEpisode {
	NarouWork getWork();
	int getEpisodeNumber();
	String getTitle();
	DateTimeFormatter NAROU_EPISODE_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
	ZonedDateTime getPostedAt();
	ZonedDateTime getLastUpdatedAt();
}
