package net.terullet.bibliothekarios.arachne.domain;

public final class UniverseId extends LongIdentifier implements Comparable<UniverseId> {
	public UniverseId(long value) {
		super(value);
	}

	@Override
	public int compareTo(UniverseId other) {
		if (other == null) {
			throw new NullPointerException();
		}
		return Long.compareUnsigned(this.getValue(), other.getValue());
	}
}
