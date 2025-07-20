package net.terullet.bibliothekarios.arachne.content.domain.model;

import net.terullet.bibliothekarios.arachne.core.domain.LongIdentifier;

public final class EpisodeId extends LongIdentifier implements Comparable<EpisodeId> {
	public EpisodeId(long value) {
		super(value);
	}

	@Override
	public int compareTo(EpisodeId other) {
		if (other == null) {
			throw new NullPointerException();
		}
		return Long.compareUnsigned(this.getValue(), other.getValue());
	}
}
