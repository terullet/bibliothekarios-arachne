package net.terullet.bibliothekarios.arachne.service.web.com.syosetu.ncode;

import java.time.ZonedDateTime;

public record NarouEpisodeResponseMetadata(String ncode, int episodeNumber, String title, ZonedDateTime postedAt, ZonedDateTime updatedAt) {
}
