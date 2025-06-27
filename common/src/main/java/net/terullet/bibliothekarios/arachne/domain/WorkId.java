package net.terullet.bibliothekarios.arachne.domain;

public final class WorkId extends LongIdentifier implements Comparable<WorkId> {
	public WorkId(long value) {
		super(value);
	}

	@Override
	public int compareTo(WorkId other) {
		if (other == null) {
			throw new NullPointerException();
		}
		return Long.compareUnsigned(this.getValue(), other.getValue());
	}
}
