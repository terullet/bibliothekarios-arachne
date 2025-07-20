package net.terullet.bibliothekarios.arachne.core.domain;

public abstract class IntegerIdentifier {
	private final int value;
	public final int getValue() {
		return this.value;
	}

	public IntegerIdentifier(int value) {
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
		return this.value == ((IntegerIdentifier) other).value;
	}
	@Override
	public final int hashCode() {
		return this.value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append('[');
		String hex = Integer.toHexString(this.value);
		for (int i = 8 - hex.length(); i-- > 0; ) {
			sb.append('0');
		}
		return sb.append(']').toString();
	}
}
