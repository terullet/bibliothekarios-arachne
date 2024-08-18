package net.terullet.bibliothekariosarachne.narou;

public class NarouEpisodeMetadata {
	private final NarouWork work;
	public final NarouWork getWork() {
		return this.work;
	}

	private final int id;
	public final int getId() {
		return this.id;
	}

	private int episodeNumber;
	public final int getEpisodeNumber() {
		return this.episodeNumber;
	}
	public final void setEpisodeNumber(int episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public NarouEpisodeMetadata(NarouWork work, int id) {
		this.work = work;
		this.id = id;
	}
}
