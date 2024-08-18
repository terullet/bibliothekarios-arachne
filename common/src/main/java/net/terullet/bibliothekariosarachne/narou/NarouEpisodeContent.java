package net.terullet.bibliothekariosarachne.narou;

public class NarouEpisodeContent {
	private final NarouEpisodeMetadata metadata;
	public final NarouEpisodeMetadata getMetadata() {
		return this.metadata;
	}

	public NarouEpisodeContent(NarouEpisodeMetadata metadata) {
		this.metadata = metadata;
	}
}
