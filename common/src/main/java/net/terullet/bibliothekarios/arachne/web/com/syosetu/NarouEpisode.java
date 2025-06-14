package net.terullet.bibliothekarios.arachne.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.web.WebEpisode;

import java.time.format.DateTimeFormatter;

public interface NarouEpisode extends WebEpisode {
	NarouWork getWork();
	DateTimeFormatter NAROU_EPISODE_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
}
