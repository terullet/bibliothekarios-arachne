package net.terullet.bibliothekarios.arachne.domain.web.com.syosetu;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Ncode(String value) implements Comparable<Ncode> {
	private static final String PATTERN_STR;
	private static final Pattern PATTERN;

	static {
		PATTERN_STR = "[nN]\\d{4}[a-zA-Z]{1,2}";
		PATTERN = Pattern.compile(PATTERN_STR);
	}

	public Ncode(String value) {
		Objects.requireNonNull(value, "Ncode MUST NOT be null");
		Matcher m = PATTERN.matcher(value);
		if (!m.matches()) {
			throw new IllegalArgumentException("Ncode MUST follow the pattern \"" + PATTERN_STR + "\", but was \"" + value + "\"");
		}
		this.value = value.toLowerCase();
	}

	@Override
	public String toString() {
		return "Ncode[" + this.value + "]";
	}

	@Override
	public int compareTo(Ncode other) {
		if (other == null) {
			throw new NullPointerException();
		}
		return this.value.compareTo(other.value);
	}
}
