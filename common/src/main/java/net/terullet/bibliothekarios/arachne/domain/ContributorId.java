package net.terullet.bibliothekarios.arachne.domain;

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
