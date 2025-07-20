package net.terullet.bibliothekarios.arachne.domain.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.core.domain.LongIdentifier;

public final class NarouNumericId extends LongIdentifier implements Comparable<NarouNumericId> {
	public NarouNumericId(long value) {
		super(value);
	}

	@Override
	public int compareTo(NarouNumericId other) {
		if (other == null) {
			throw new NullPointerException();
		}
		return Long.compareUnsigned(this.getValue(), other.getValue());
	}
}
