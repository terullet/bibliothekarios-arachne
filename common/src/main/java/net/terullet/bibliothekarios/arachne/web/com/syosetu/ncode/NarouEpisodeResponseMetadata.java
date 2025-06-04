package net.terullet.bibliothekarios.arachne.web.com.syosetu.ncode;

import net.terullet.bibliothekarios.arachne.web.com.syosetu.NarouWork;

import java.time.ZonedDateTime;

public record NarouEpisodeResponseMetadata(NarouWork work, int episodeNumber, String title, ZonedDateTime postedAt, ZonedDateTime updatedAt) {
}
