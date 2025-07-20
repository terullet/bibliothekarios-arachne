package net.terullet.bibliothekarios.arachne.contribution.domain.model;

import net.terullet.bibliothekarios.arachne.core.domain.LongIdentifier;

public final class ContributorId extends LongIdentifier implements Comparable<ContributorId> {
	public ContributorId(long value) {
		super(value);
	}

	@Override
	public int compareTo(ContributorId other) {
		if (other == null) {
			throw new NullPointerException();
		}
		return Long.compareUnsigned(this.getValue(), other.getValue());
	}
}
