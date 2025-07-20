package net.terullet.bibliothekarios.arachne.content.domain.model;

import net.terullet.bibliothekarios.arachne.core.domain.LongIdentifier;

public final class EpisodePostId extends LongIdentifier implements Comparable<EpisodePostId> {
	public EpisodePostId(long value) {
		super(value);
	}

	@Override
	public int compareTo(EpisodePostId other) {
		if (other == null) {
			throw new NullPointerException();
		}
		return Long.compareUnsigned(this.getValue(), other.getValue());
	}
}
