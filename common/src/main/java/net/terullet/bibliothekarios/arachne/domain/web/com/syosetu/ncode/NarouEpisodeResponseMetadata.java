package net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.ncode;

import java.time.ZonedDateTime;

public record NarouEpisodeResponseMetadata(String ncode, int episodeNumber, String title, ZonedDateTime postedAt, ZonedDateTime updatedAt) {
}
