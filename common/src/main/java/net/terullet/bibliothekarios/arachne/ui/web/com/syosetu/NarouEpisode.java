package net.terullet.bibliothekarios.arachne.ui.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.ui.web.Episode;

import java.time.format.DateTimeFormatter;

public interface NarouEpisode extends Episode {
	NarouWork getWork();
	DateTimeFormatter NAROU_EPISODE_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
}
