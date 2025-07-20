package net.terullet.bibliothekarios.arachne.hyperlink.domain.model;

import net.terullet.bibliothekarios.arachne.core.domain.LongIdentifier;

public final class HyperlinkId extends LongIdentifier implements Comparable<HyperlinkId> {
	public HyperlinkId(long value) {
		super(value);
	}

	@Override
	public int compareTo(HyperlinkId other) {
		if (other == null) {
			throw new NullPointerException();
		}
		return Long.compareUnsigned(this.getValue(), other.getValue());
	}
}
