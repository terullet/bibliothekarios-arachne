package net.terullet.bibliothekarios.arachne.domain;

public abstract class LongIdentifier {
	private final long value;
	public final long getValue() {
		return this.value;
	}

	public LongIdentifier(long value) {
		if (value <= 0) {
			throw new IllegalArgumentException("Identifier value MUST be positive, but was: " + value);
		}
		this.value = value;
	}

	@Override
	public final boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (this.getClass() != other.getClass()) return false;
		return this.value == ((LongIdentifier) other).value;
	}
	@Override
	public final int hashCode() {
		return Long.hashCode(this.value);
	}

	@Override
	public final String toString() {
		String hex = Long.toHexString(this.value);
		StringBuilder sb = new StringBuilder(64).append(this.getClass().getSimpleName()).append('[');
		for (int i = 16 - hex.length(); i-- > 0; ) {
			sb.append('0');
		}
		return sb.append(']').toString();
	}
}
