package net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.ncode;

import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouWorkType;

public record NarouEpisodeNcodeQuery(String ncode, String narouId, boolean isR18, NarouWorkType workType, int episodeNumber) {
}
