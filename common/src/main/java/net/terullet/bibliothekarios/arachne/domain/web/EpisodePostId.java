package net.terullet.bibliothekarios.arachne.domain.web;

import net.terullet.bibliothekarios.arachne.domain.LongIdentifier;

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
